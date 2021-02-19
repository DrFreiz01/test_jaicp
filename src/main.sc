require: requirements.sc
theme: /

    state: Start
        q!: $regex</start>
        q!: *  (*ривет*|*равс*|*рас*|ghbd*) *
        q!: * (сначал*|занов*) *
       
        if: $client.Name
            a: Здравствуйте, {{$client.Name}}! Я -- бот туристической компании "Just Tour". Я могу рассказать о погоде в любой точке мира и помочь оформить тур.
            
            script:
                $jsapi.startSession();
                
        else:
            a: Здравствуйте! Я -- бот туристической компании "Just Tour". Я могу рассказать о погоде в любой точке мира и помочь оформить тур.
            script:
                $jsapi.startSession();
                

        buttons:
            "Оформить тур" -> /CreateTour/NewTour
            "Узнать погоду" -> /FindWeather/Weather
    
    state: Restart
        q!: * (друг*|нов*) (заявк*|тур*|поезд*|путешест*|пакет*) *
        
        if: $client.Name
            a: Здравствуйте, {{$client.Name}}! Я -- бот туристической компании "Just Tour".
            script:
                $jsapi.startSession();
               
        else:
            a: Здравствуйте! Я -- бот туристической компании "Just Tour".
            script:
                $jsapi.startSession();
                $reactions.newSession();
        go!: /CreateTour/NewTour
        
    state: Debug
        q!: инфо
        a: {{toPrettyString($session)}}

        
    
    state: WhatElse
        a: Всего хорошего!
        script: 
            $jsapi.stopSession();
    
    state: ResetSessionSoft
        event: sessionDataSoftLimitExceeded    
        script:
            delete session.text;
    
    state: ResetClientSoft
        event: clientDataSoftLimitExceeded
        script: 
            delete client.text;
    
    state: ResetSessionHard
        event: sessionDataHardLimitExceeded
        script: 
            delete session.text;
    
    state: ResetClientHard
        event: clientDataHardLimitExceeded
        script:
            delete client.text;
            

    state: Bye
        a: Пока пока

    state: NoMatch
        event: noMatch
        a: Я не понял. Вы сказали: {{$request.query}}. Выберите один из вариантов.
        buttons:
            "Оформить тур" -> /CreateTour/NewTour
            "Узнать погоду" -> /FindWeather/Weather

    