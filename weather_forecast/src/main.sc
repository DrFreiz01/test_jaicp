# Require tag is used to connect files to the main script from the same project or from the other projects declared in chatbot.yaml in the 'dependencies:' block.
require: patterns.sc
    module = sys.zb-common

require: dateTime/moment.min.js
    module = sys.zb-common

require: slotfilling/slotFilling.sc
    module = sys.zb-common

require: scripts/functions.js

theme: /Weather
    # The bot provides weather forecast up to 15 days.
    
    # First, the bot will check the API key and, if it is missing, it will ask the user to set it up.
    # The bot will briefly present itself for the new user.
    state: Start
        q!: * *start
        a: Здравствуйте!
        if: $jsapi.context().injector.weatherApiKey
            if: !$client.registered
                a: Я могу рассказать вам о погоде в любой точке мира на сегодня или в любой другой день, на две недели вперёд.
                script:
                    $client.registered = true;
            a: Пожалуйста, назовите город, который вас интересует.
        else:
            a: Отсутствует ключ API. Для работы с сервисом погоды, пожалуйста, добавьте его в файл: chatbot.yaml.
    
    # noContext = true flag means that the next request will be processed in the context of the previous state.
    # Read more about noContext flag: https://help.just-ai.com/#/docs/en/JAICP_DSL/tags/declarative_tags/state
    state: YouAreWelcome || noContext = true
        intent!: /YouAreWelcome
        random:
            a: Всегда рад помочь!
            a: Пожалуйста.
            a: Обращайтесь. 
    
    state: HowAreYou || noContext = true
        intent!: /HowAreYou
        random:
            a: Хорошо.
            a: Неплохо.
            a: Замечательно.
        
    # Forecast for the current date, when only the city name is given.   
    state: City
        intent!: /WeatherCity
        script:
            $session.date = $jsapi.dateForZone("Europe/Moscow", "yyyy-MM-dd");
            $session.city =  $parseTree.City[0].value.name;
            $session.diff = 0;
        go!: /Weather/GetWeather
        
    # Forecast for the specified city and date, when both city name and date are given.   
    state: CityDate
        intent!: /WeatherCityDate
        script:
            $session.date = $parseTree["duckling.time"][0].value.timestamp;
            $session.city = $parseTree.City[0].value.name;
            $session.diff = getDateDiff($session.date);
        if: ($session.diff < 16 && $session.diff >= 0)
            go!: /Weather/GetWeather
        else:
            a: Я могу назвать погоду только на 15 дней вперёд. Пожалуйста, назовите дату в пределах этого срока.
        
        # If a new date is specified, the forecast will be given for the new date and the last mentioned city.
        state: OtherDate
            intent: /DateOnlyDay
            intent: /DateOnlyDay || fromState = "/Weather/GetWeather"
            intent: /OtherDate
            intent: /OtherDate || fromState = "/Weather/GetWeather"
            script:
                $session.date = $parseTree.DateOnlyDay ? new Date(currentDate().year(), currentDate().month(), $parseTree.DateOnlyDay[0].value) : $parseTree["duckling.time"][0].value.timestamp;
                $session.diff = getDateDiff($session.date);
            if: ($session.diff < 16 && $session.diff >= 0)
                go!: /Weather/GetWeather
            else:
                a: Я могу назвать погоду только на 15 дней вперёд. Пожалуйста, назовите дату в пределах этого срока.
        
    # State to get weather info.
    state: GetWeather
        script:
            $temp.weather = getCurrentWeather($session.city, $session.diff);
            $temp.cityFull = inflectCity($session.city);
            $temp.date = moment($session.date).locale("ru").format('Do MMMM');
        
        # Provide forecast, if the weather info is not empty.
        if: $temp.weather.temperature && $temp.weather.description
            random:
                a: {{$temp.cityFull}} {{$temp.date}} {{$temp.weather.description}} и {{$temp.weather.temperature}}°C.
                a: {{$temp.cityFull}} {{$temp.date}} будет такая погода: {{$temp.weather.temperature}}°C и {{$temp.weather.description}}.
                a: {{$temp.cityFull}} {{$temp.date}} ожидается такая погода: {{$temp.weather.temperature}}°C и {{$temp.weather.description}}.
        else: 
            a: Не удалось найти прогноз для этого города. 
        
        state: OtherCity
            intent: /OtherCity
            script:
                $session.city = $parseTree.City[0].value.name;
            go!: /Weather/GetWeather 
    
    state: CatchAll || noContext = true
        event!: noMatch
        random:
            a: Извините, мне не совсем понятно.
            a: Я запутался. Давайте начнём сначала?
            a: Простите, я вас не понимаю.
            a: Похоже, я не совсем вас понимаю.

        