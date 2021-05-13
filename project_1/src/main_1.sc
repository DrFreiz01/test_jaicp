# patterns:
#     #$dateLetters = ( $regexp<\d{1,2}( |-)?(ого|го|uj|juj)?( |-)?(январ[а-яА-Я]*|феврал[а-яА-Я]*|март[а-яА-Я]*|апрел[а-яА-Я]*|ма[йя]|июн[а-яА-Я]*|июл[а-яА-Я]*|август[а-яА-Я]*|сентябр[а-яА-Я]*|октябр[а-яА-Я]*|ноябр[а-яА-Я]*|декабр[а-яА-Я]*)(( |-)*(20?\d{2} ( |г)))?>)
#     $dateLetters = ( $regexp<\d{1,2}( |-)?(ого|го|uj|juj)?( |-)?(январ[а-яА-Я]*|феврал[а-яА-Я]*|март[а-яА-Я]*|апрел[а-яА-Я]*|ма[йя]|июн[а-яА-Я]*|июл[а-яА-Я]*|август[а-яА-Я]*|сентябр[а-яА-Я]*|октябр[а-яА-Я]*|ноябр[а-яА-Я]*|декабр[а-яА-Я]*)(( |-)*(20?\d{2} ( |г)))?> | $regexp<(?ui)(январ[а-яА-Я]*|феврал[а-яА-Я]*|март[а-яА-Я]*|апрел[а-яА-Я]*|ма[йя]|июн[а-яА-Я]*|июл[а-яА-Я]*|август[а-яА-Я]*|сентябр[а-яА-Я]*|октябр[а-яА-Я]*|ноябр[а-яА-Я]*|декабр[а-яА-Я]*)( |-)*\d{1,2}( |-)?(ого|го|uj|juj)?( |-)?( |-)?((20)?\d{2} ?г?)?> | {$regexp<(?ui)\d{1,2}( |-)?(ого|го|uj|juj)?> числ*} [$regexp<(20)?\d{2} ?г?>])
#     $numberDate = $regexp<\d{1,2}>

# patterns:
#     $test = ( asd{{$session.mytest}})


# theme: /
#     state: start
#         q!: * *start
#         a: Go!

#     state: test1 
#         q!: test2DatesParseTree * ($dateLetters::dateLetters1) [$oneWord] [$oneWord] [на] ($dateLetters::dateLetters2) *
#         a: {{toPrettyString($parseTree)}} 
 
#     state: test2
#         q!: $dateLetters и $dateLetters
#         a: {{toPrettyString($parseTree)}}

require: dateTime/dateTime.sc
    module = sys.zb-common
    
require: common.js
    module = sys.zb-common

        
        
theme: /

    state: start
        q!: * *start
        a: Go!
        script:
        
            # function currentDate() {
            #     var time = moment.utc($jsapi.currentTime());

            #     var $rd = $jsapi.context().request.data;
            #     if ($rd && typeof $rd.offset != 'undefined') {
            #         time = time.utcOffset($rd.offset);
            #     }
            #     return time;
            # }

            var interval = currentDate().valueOf();
            $reactions.answer(JSON.stringify(interval));
    # state: asd
    #     q!: $mytest
    #     a: TEST ASD
        
    # state: ConversationStart || noContext = true
    #     event: newChatStarted
    #     script:
    #         $session.startNewSession = true;
    #         if ($session.startNewSession){
    #             createNewSession($request)
    #         }
    #         $session.location = $request.rawRequest.location;
            
    state: fileEvent
        event!: fileEvent
        a: fileEvent TEST OK!
        
#     # state: test1 
#     #     q!: $numberDate
#     #     a: {{toPrettyString($parseTree)}}
        
#     # state: test1 
#     #     q!: $numberDate::dateLetters1 и $numberDate::dateLetters2
#     #     a: {{toPrettyString($parseTree)}}
        
#     state: test2
#         q!: $dateLetters и $dateLetters
#         a: {{toPrettyString($parseTree)}}
    
#     # state: test3
#     #     q!: asd
#     #     a: {{ toPrettyString($request.rawRequest.data) }}
    
#     state: CatchAll || noContext=true
#         event!: noMatch
#         a: Я вас не понимаю.

# patterns:
#     $dateLetters = ( $regexp<(?ui)\d{1,2}( |-)?(ого|го|uj|juj)?( |-)?(январ[а-яА-Я]*|феврал[а-яА-Я]*|март[а-яА-Я]*|апрел[а-яА-Я]*|ма[йя]|июн[а-яА-Я]*|июл[а-яА-Я]*|август[а-яА-Я]*|сентябр[а-яА-Я]*|октябр[а-яА-Я]*|ноябр[а-яА-Я]*|декабр[а-яА-Я]*)(( |-)*(20)?\d{2} ?г?)?> | $regexp<(?ui)(январ[а-яА-Я]*|феврал[а-яА-Я]*|март[а-яА-Я]*|апрел[а-яА-Я]*|ма[йя]|июн[а-яА-Я]*|июл[а-яА-Я]*|август[а-яА-Я]*|сентябр[а-яА-Я]*|октябр[а-яА-Я]*|ноябр[а-яА-Я]*|декабр[а-яА-Я]*)( |-)*\d{1,2}( |-)?(ого|го|uj|juj)?( |-)?( |-)?((20)?\d{2} ?г?)?> | {$regexp<(?ui)\d{1,2}( |-)?(ого|го|uj|juj)?> числ*} [$regexp<(20)?\d{2} ?г?>]) 
#     $numberDate = $regexp<\d{1,2}>

# theme: /

#     state: start
#         q!: * *start
#         a: Go!
        
#     state: test2
#         q!: $dateLetters и $dateLetters
#         a: {{toPrettyString($parseTree)}}
    
#     state: CatchAll || noContext=true
#         event!: noMatch
#         a: Я вас не понимаю.

# theme: /

#     state: start
#         q!: * *start
#         a: Go!
        
#     # state: switch
        
#     state: sendFile
#         event!: fileEvent
#         a: fileEvent TEST OK!
        



