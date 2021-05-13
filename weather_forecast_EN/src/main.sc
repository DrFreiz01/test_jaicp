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
        a: Hello!
    # $jsapi.startSession() means that each time the bot starts dialogue - new session is started as well.
    # Please find additional info about session lifetime control here: https://help.just-ai.com/#/docs/en/JS_API/session_lifetime_control    
        script:
            $jsapi.startSession();
        if: $jsapi.context().injector.weatherApiKey
            if: !$client.registered
                a: I can tell you about the weather anywhere in the world today or provide a weather forecast for any day up to two weeks ahead.
                script:
                    $client.registered = true;
            a: What city would you like to know the weather for?
        else:
            a: API key is missing. In order to use the weather forecasting tool, please add the key in the file chatbot.yaml.
    
    # noContext = true flag means that the next request will be processed in the context of the previous state.
    # Read more about noContext flag: https://help.just-ai.com/#/docs/en/JAICP_DSL/tags/declarative_tags/state
    state: YouAreWelcome || noContext = true
        intent!: /YouAreWelcome
        random:
            a: Happy to be of help!
            a: You are always welcome.
            a: My pleasure.
    
    # The bot replies to the user's "What can you do?" question.
    state: IForecastWeather || noContext = true
        intent!: /WhatCanYouDo
        intent!: /WhatCanYouDo | fromState = "/Start"
        a: I can tell you about the weather anywhere in the world today or provide a weather forecast for any day up to two weeks ahead.
        a: What city would you like to know the weather for?
    
    # The bot replies to the user's "How are you?" question.
    state: IAmFine || noContext = true
        intent!: /HowAreYou
        random:
            a: I am fine, thank you for asking! What city would you like to know the weather for?
            a: Doing great, thanks for asking! What city would you like to know the weather for?

    # Forecast for the current date, when only the city name is given.   
    state: City
        intent!: /WeatherCity
        script:
            $session.date = $jsapi.dateForZone("Europe/Moscow", "yyyy-MM-dd");
            $session.city = $parseTree.City[0].value.name;
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
            a: I can only forecast weather for the next 15 days. Please choose a date within this time frame.
        
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
                a:  I can only forecast weather for the next 15 days. Please choose a date within this time frame.
        
    # State to get weather info.
    state: GetWeather
        script:
            $temp.weather = getCurrentWeather($session.city, $session.diff);
            $temp.cityFull = inflectCity($session.city);
            $temp.date = moment($session.date).locale("en").format('Do MMMM');
        
        # Provide forecast, if the weather info is not empty.
        if: $temp.weather.temperature && $temp.weather.description
            random:
                a: The following forecast is available for {{$temp.cityFull}} for {{$temp.date}}: {{$temp.weather.description}} and {{$temp.weather.temperature}}°C.
                a: On {{$temp.date}}, the weather in {{$temp.cityFull}} will be as follows: {{$temp.weather.temperature}}°C and {{$temp.weather.description}}.
                a: On {{$temp.date}}, the following weather is forecast in {{$temp.cityFull}}: {{$temp.weather.temperature}}°C and {{$temp.weather.description}}.
        else: 
            a: I could not find the weather forecast for this city. 
        
        state: OtherCity
            intent: /OtherCity
            script:
                $session.city = $parseTree.City[0].value.name;
            go!: /Weather/GetWeather 
    
    state: CatchAll || noContext = true
        event!: noMatch
        random:
            a: Sorry, I did not quite catch that.
            a: Looks like I got confused. Could we start over please?
            a: Could you repeat that for me? I did not get that.
            a: Sorry, I did not understand that. Could you repeat that please?

        