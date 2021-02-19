theme: /CreateTour
    
    state: NewTour
        intent!: /NewTour_First
        script:
            $session.City = $parseTree._City;
            $session.StartDate_trip = $parseTree._StartDate; 
        if: $session.City
            go!: /CreateTour/NewTour/ResumeCreatingNewTour
        else:
            a: Какая страна или какой город Вас интересует?
        
        state: Set_City
            q: * @City * 
            script:
                $session.City = $parseTree._City;
            go!: /CreateTour/NewTour/ResumeCreatingNewTour
       
        
        state: No_City
            event: noMatch
            a: Менеджер Вам поможет выбрать лучшее направление.
            script:
                $session.City = "-";
            go!: /CreateTour/NewTour/ResumeCreatingNewTour
        
        state: WeatherRequest
            q: * (погод*|прогноз*|градус*|температур*)
            go!: /FindWeather/Weather/WeatherInTour        
        
        state: ResumeCreatingNewTour
            if: $session.Duration && $session.StartDate_trip
                if: $session.NumPeople
                    if: $session.NumChildren || $session.NumChildren === 0
                        if: $session.HotelStars 
                            if: $session.Money 
                                go!: /CreateTour/NewTour/CreateNewTour/GetComment
                            else:
                                go!: /CreateTour/NewTour/CreateNewTour/CheckSum
                        else:
                            go!: /CreateTour//NewTour/CreateNewTour/CheckStars
                    else: 
                        go!: /CreateTour/NewTour/CreateNewTour/CheckChildren
                else:
                    go!: /CreateTour/NewTour/CreateNewTour/CheckPeople
            else:
                go!: /CreateTour/NewTour/GetNameAndPhone
        
        state: CheckingDataForTour 
            
                
            if: $session.Duration 
                if: $session.StartDate_trip 
                    if: $session.NumPeople 
                        if: $session.NumChildren  || $session.NumChildren === 0
                            if: $session.HotelStars 
                                if: $client.Name 
                                    if: $session.Money 
                                        go!: /CreateTour/NewTour/CreateNewTour/GetComment
                                    else:
                                        go!: /CreateTour/NewTour/CreateNewTour/CheckSum
                                else: 
                                    go!: /CreateTour/NewTour/CreateNewTour/CheckName
                            else:
                                go!: /CreateTour/NewTour/CreateNewTour/CheckStars
                        else: 
                            go!: /CreateTour/NewTour/CreateNewTour/CheckChildren
                    else:
                        go!: /CreateTour/NewTour/CreateNewTour/CheckPeople
                else: 
                    go!: /CreateTour/NewTour/CreateNewTour/CheckStartdate
            else: 
                go!: /CreateTour/NewTour/CreateNewTour
         
        state: GetNameAndPhone
            if: $client.Phone && $client.Name
                go!: /CreateTour/NewTour/CheckContactData
            else:
                go!: /CreateTour/NewTour/AskContactData
        
        state: CheckContactData
            a: Ваши контактные данные: 
            a: Имя: {{$client.Name}}
            a: Телефон: {{$client.Phone}}
            a: Всё верно?
            buttons:
                "Да" -> Yes
                "Нет" -> No
            
            state: Yes
                q: * @Agree *
                go!: /CreateTour/NewTour/CreateNewTour
            
            state: No 
                q: * @Disagree *
                script:
                    $client = {};
                go!: /CreateTour/NewTour/AskContactData
            
            state: NoMatch
                event: noMatch
                a: Я не понял. Вы сказали: {{$request.query}}.
                a: Вы можете ответить, например, "да" или "нет".
                go!: /CreateTour/NewTour/CheckContactData
            
        state: AskContactData || modal=true
            a: Представьтесь и введите свой номер телефона, пожалуйста.
            
            state: GetData 
                intent: /Person
                script:
                    $client.Phone = $parseTree._Phone
                    $client.Name = $parseTree._Name
                
                if: $client.Phone && $client.Name
                    go!: /CreateTour/NewTour/CreateNewTour
                   
            state: NoMatch
                event: noMatch
                if: $client.Phone
                    a: Как к Вам можно обращаться?
                elseif: $client.Name
                    a: Сообщите, пожалуйста, телефон для связи в международном десятизначном формате, например, 89113445593 или в городском шестизначном, например, 451111, или семизначном формате, например, 4111111.
                else: 
                    go!: /CreateTour/NewTour/AskContactData
            
            
        state: CreateNewTour
            a: Давайте заполним заявку. На сколько дней Вы бы хотели отправиться в тур?
            go: ToursParent
            
            state: ToursParent
                
                state: GetInfo
                    intent: /NewTour_intent
                    script:
                        $session.Duration = $parseTree._Duration
                        $session.NumPeople = $parseTree._NumPeople
                        $session.NumChildren = $parseTree._NumChildren
                        $session.Money = $parseTree._Money
                        if(!$session.StartDate_trip){
                            if ($parseTree._StartDate === '01.01.1970') {
                                $session.StartDate_trip = '-'
                            }
                            else {
                                $session.StartDate_trip = $parseTree._StartDate
                            }
                        }
                        
                        $session.HotelStars = $parseTree._HotelStars
                    go!: /CreateTour/NewTour/CheckingDataForTour
                    
                state: NoMatch 
                    event: noMatch
                    a: Я Вас не понял. Вы сказали: {{$request.query}}.
                    go!: /CreateTour/NewTour/CreateNewTour
                
            state: CheckStartdate
                a: Когда Вы хотели бы отправиться в путешествие? 
                
                state: GetValue
                    intent: /GetStartdate
                    script:
                        if ($parseTree._StartDate === '01.01.1970') {
                            $session.StartDate_trip = '-'
                        }
                        else {
                        $session.StartDate_trip = $parseTree._StartDate
                        }        
                    go!: /CreateTour/NewTour/CheckingDataForTour
                state: Season
                    intent: /Season
                    script:
                        $session.StartDate_trip = $parseTree._Season
                    go!: /CreateTour/NewTour/CheckingDataForTour    
                
                state: WithoutValue    
                    q: * @Dont_know * $weight<+0.3>
                    script:
                        $session.StartDate_trip = '-'
                    go!: /CreateTour/NewTour/CheckingDataForTour
                        
                state: NoMatch
                    event: noMatch
                    a: Введите, пожалуйста, корректную дату в формате 'dd.mm.yyyy'. Например, 01.02.2021.
                    go!: /CreateTour/NewTour/CreateNewTour/CheckStartdate
                
            state: CheckPeople
                a: Сколько человек поедет?
                
                state: GetValue
                    intent: /GetNumberPeople
                    script:
                        $session.NumPeople = $parseTree._NumPeople
                    go!: /CreateTour/NewTour/CheckingDataForTour
                    
                state: NoMatch
                    event: noMatch
                    a: Я не понял. Вы сказали: {{$request.query}}.   
                    a: Укажите число людей, например "4".
                    go!: /CreateTour/NewTour/CreateNewTour/CheckPeople
                
                    
            state: CheckChildren
                a: Сколько детей поедет?
                
                state: GetValue
                    intent: /GetNumberChildren
                    script:
                        $session.NumChildren = $parseTree._NumChildren
                    go!: /CreateTour/NewTour/CheckingDataForTour

                state: NoMatch
                    event: noMatch
                    a: Я не понял. Вы сказали: {{$request.query}}.  
                    a: Укажите число детей, например "4".
                    go!: /CreateTour/NewTour/CreateNewTour/CheckChildren
                        
                        
            state: CheckSum
                a: Какой бюджет у планируемой поездки?
                
                state: GetValue
                    intent: /GetMoney
                    script:
                        $session.Money = $parseTree._Money
                    go!: /CreateTour/NewTour/CreateNewTour/GetComment
                    
                state: NoMatch
                    event: noMatch
                    a: Я не понял. Вы сказали: {{$request.query}}.  
                    a: Вы можете ответить, например, "10000".
                    go!: /CreateTour/NewTour/CreateNewTour/CheckSum
                
            state: CheckStars
                a: Сколько звёзд должно быть у отеля?
                buttons:
                    "1 звезда"
                    "2 звезды"
                    "3 звезды"
                    "4 звезды"
                    "5 звезд"
                    "Не важно"
                
                state: NoMatch
                    event: noMatch
                    a: Я не понял. Вы сказали: {{$request.query}}.   
                    a: Вы можете просто нажать кнопку с нужным количеством звёзд или ввести вручную.
                    go!: /CreateTour/NewTour/CreateNewTour/CheckStars    
                
                state: Mark
                    intent: /Hotel
                    script:
                        $session.HotelStars = $parseTree._HotelStars
                    if: $client.Name
                        if: $session.Money
                            go!: /CreateTour/NewTour/CreateNewTour/GetComment
                        else:
                            go!: /CreateTour/NewTour/CreateNewTour/CheckSum
                    else: 
                        go!:  /CreateTour/NewTour/CreateNewTour/CheckName
                
            state: GetComment
                a: Оставите комментарий для менеджера?
                buttons:
                    "Да" -> Yes
                    "Нет" -> No
                
                state: Yes
                    q: * @Agree *
                    go!: /CreateTour/NewTour/CreateNewTour/GetTextOfComment
                
                state: No 
                    q: * @Disagree *
                    go!: /CreateTour/NewTour/CreateNewTour/GetTextOfComment/NoComment
                    
                state: NoMatch
                    event: noMatch
                    a: Я не понял. Вы сказали: {{$request.query}}.
                    a: Вы можете ответить, например, "да" или "нет".
                    go!: /CreateTour/NewTour/CreateNewTour/GetComment
                    
            state: GetTextOfComment || modal=true
                a:  Оставьте, пожалуйста, комментарий для менеджера.
               
                state: Comment || noContext=true
                    event: noMatch
                    script:
                        $session.Сomment = $request.query
                    go!: /CreateTour/NewTour/CreateNewTour/GetTextOfComment/Send
                
                state: NoComment
                    script:
                        $session.Сomment = "-"
                    go!: /CreateTour/NewTour/CreateNewTour/GetTextOfComment/Send
                    
                state: Send 
                    script:
                        customSendEmail($session,$client);
                    go!: /WhatElse        
                    