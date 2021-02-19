theme: /FindWeather
    
    state: Weather
        a: Погода в какой стране или в каком городе и в какой день Вас интересует?
        
        state: WeatherInTour || modal=true
            #q!: * (погод*|прогноз*|температур*) * 
            
            if: $session.City && $session.City != '-'
                script:
                    $session.City_weather = $session.City
                if: $session.StartDate_trip == 'Зима' || $session.StartDate_trip == 'Весна' || $session.StartDate_trip == 'Лето' || $session.StartDate_trip == 'Осень'
                    a: Укажите, пожалуйста, корректную дату в формате 'dd.mm.yyyy' не раньше текущего дня. Например, 01.02.2021.
                elseif: $session.StartDate_trip && $session.StartDate_trip != '-'
                    script:
                        $session.StartDate = $session.StartDate_trip
                    go!: /FindWeather/Weather/CheckData
                else:
                    a: Укажите, пожалуйста, корректную дату в формате 'dd.mm.yyyy' не раньше текущего дня. Например, 01.02.2021.
            else:
                a: Укажите, какая страна или какой город Вас интересует.
             
            state: Set_City_1
                intent: /Weather_City
                script:
                    $session.City_weather = $parseTree._City;
                    #$session.City = $session.City_weather;
                    
                if: $session.StartDate
                    go!: /FindWeather/Weather/CheckData
                elseif: $session.StartDate_trip
                    script:
                        $session.StartDate = $session.StartDate_trip
                    go!: /FindWeather/Weather/CheckData
                else:    
                    a: Укажите, пожалуйста, корректную дату в формате 'dd.mm.yyyy' не раньше текущего дня. Например, 01.02.2021.
            
            
            state: Set_Date_1
                intent: /Weather_Date
                script:
                    $session.StartDate = $parseTree._Date;
                        
                if: $session.City_weather
                    go!: /FindWeather/Weather/CheckData
                else:
                    a: Укажите город или страну.
            
            state: NoMatch
                event: noMatch
                if: $session.City_weather
                    a: Я не понял. Вы сказали: {{$request.query}}. Введите дату в формате 'dd.mm.yyyy' не раньше текущего дня. Например, 01.02.2021.
                elseif: $session.StartDate
                    a: Я не понял. Вы сказали: {{$request.query}}. Введите город.
                else: 
                    a: Я не понял. Вы сказали: {{$request.query}}. Введите город и дату в формате 'dd.mm.yyyy' не раньше текущего дня. Например, 01.02.2021.
        
        state: Set_City
            intent: /Weather_City
            script:
                $session.City_weather = $parseTree._City;
                #$session.StartDate = $parseTree._Date
                #$session.City = $session.City_weather;
                
            if: $session.StartDate
                go!: /FindWeather/Weather/CheckData
            else:
                a: Укажите, пожалуйста, корректную дату в формате 'dd.mm.yyyy' не раньше текущего дня. Например, 01.02.2021.
            
            
        state: Set_Date
            intent: /Weather_Date
            script:
                $session.StartDate = $parseTree._Date;
                #$session.City_weather = $parseTree._City;
                
            if: $session.City_weather
                go!: /FindWeather/Weather/CheckData
            else:
                a: Укажите город или страну.
            
        
        state: GetWeather 
            intent!: /Weather   
            script:
                $session.StartDate = $parseTree._StartDate;
                $session.City_weather = $parseTree._City;
            #a: {{$session.City_weather}}
           # script:
            #    $session.City_weather = $nlp.inflect($session.City_weather, "nomn");
            #a: {{$session.City_weather}}    
                
            go!: /FindWeather/Weather/CheckData
           
        state: NoMatch
            event: noMatch
            if: $session.City_weather
                a: Укажите, пожалуйста, какая дата Вас интересует. Дата должна быть не раньше текущего дня и указывана в формате 'dd.mm.yyyy'. Например, 01.02.2021.
            elseif: $session.StartDate
                a: Укажите, какая страна или какой город Вас интересует.
            else: 
                a: Погода в какой стране или в каком городе и в какой день Вас интересует?
            
        state: CheckData
            script:
                $session.now = Date.now();
                
            if: $session.now - $session.StartDate.timestamp > 86400000 || $session.StartDate.timestamp - $session.now > 1296000000
                a: Дата должна быть не раньше текущей и не позже 15 дней от текущей. Введите другую дату в формате 'dd.mm.yyyy'. Например, 01.02.2021. 
                go!: /FindWeather/Weather/CheckData/AskDate
            
            #elseif: $session.StartDate.timestamp - $session.now > 1296000000
            #    a: Дата должна быть не позже 15 дней от текущей. Введите другую дату. 
            #    go!: /FindWeather/Weather/CheckData/AskDate
            else: 
                go!: /FindWeather/Weather/WeatherCode
            
            state: AskDate
                
                state: Date
                    intent: /Date
                    script:
                        $session.StartDate = $parseTree._Date;
                    go!: /FindWeather/Weather/CheckData    

        state: WeatherCode
            script:
                weather_state($session);
            
            state: Yes 
                q: * (@Agree|оформит* тур*|оформит*|тур*|хочу|заявк*|заявк* * тур*)
                if: $session.City
                    a: Хотите оформить новый тур в локацию {{$session.City_weather}} или продолжить оформление старого тура в локацию {{$session.City}}?
                    buttons: 
                        "Новый" -> /FindWeather/Weather/WeatherCode/Yes/NewTourInWeather 
                        "Старый" -> /FindWeather/Weather/WeatherCode/Yes/PreviousTour
                else:
                    go!: /FindWeather/Weather/WeatherCode/Yes/NewTourInWeather
                
                state: PreviousTour
                    q: * @PreviousTour * 
                    go!: /CreateTour/NewTour/CheckingDataForTour 
                
                state: NewTourInWeather
                    q: * @NewTour *
                    script:
                        $session.City = $session.City_weather;
                    go!: /CreateTour/NewTour/GetNameAndPhone
                
                state: noMatch
                    event: noMatch
                    a: Я Вас не понял.
                    go!: /FindWeather/Weather/WeatherCode/Yes
            
            state: No
                q: * @Disagree *
                
                a: Может быть, Вы хотите узнать погоду в другой точке мира?
                
                state: No
                    q: * @Disagree *
                    a: Может быть, Вы хотите оформить путёвку в другую точку мира? 
                    
                    state: Yes 
                        q: * @Agree *
                        script:
                           $session.City = {};
                        go!: /CreateTour/NewTour
                        
                    state: No 
                        q: * @Disagree *
                        a: Жаль, что не смог Вам помочь. Всего хорошего!
                    
                    state: NoMatch
                        event: noMatch
                        a: Я не понял. Вы сказали: {{$request.query}}.  
                        a: Вы можете ответить, например, "да" или "нет".
                        go!: /FindWeather/Weather/WeatherCode/No/No
                
                state: Yes
                    q: * @Agree 
                    
                    go!: /FindWeather/Weather
                
                state: YesCity
                    intent: /Weather_City
                    script:
                        $session.City_weather = $parseTree._City;
                    go!: /FindWeather/Weather/WeatherCode
                    
                state: NoMatch
                    event: noMatch
                    a: Я не понял. Вы сказали: {{$request.query}}. Вы можете  ответить, например, "да" или "нет".
                    go!: /FindWeather/Weather/WeatherCode/No
                    
            state: Change_City
                intent: /Weather_City
                script:
                    $session.City_weather = $parseTree._City;
                
                go!: /FindWeather/Weather/WeatherCode
                
                state: NoMatch
                    event: noMatch
                    a: Я вас не понял. Вы сказали: {{$request.query}}. Введите город.
                    go!: /FindWeather/Weather/WeatherCode/Change_City
            
            state: Change_Date
                intent: /Weather_Date
                script:
                    $session.StartDate = $parseTree._Date;
                go!: /FindWeather/Weather/CheckData   
                
                state: NoMatch
                    event: noMatch
                    a: Я вас не понял. Вы сказали: {{$request.query}}. Введите другую дату в формате 'dd.mm.yyyy'. Например, 01.02.2021.
                    go!: /FindWeather/Weather/WeatherCode/Change_Date
            
            state: NoMatch
                event: noMatch
                a: Я не понял. Вы сказали: {{$request.query}}.   
                a: Вы можете ответить, например, "да" или "нет".
                go!: /FindWeather/Weather/WeatherCode
        
        