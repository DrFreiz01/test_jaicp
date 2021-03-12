patterns:
    $cat = (кот/котик/котя)

theme: /

    state: start
        q!: * *start
        a: Go!
    
    state:
        q!: asd
        a: asd ok test commit!
        script:
            ('injector = ' + log(toPrettyString($injector)));
        
        state: Block1
            q: кот
            a: котик block1!
            
        state:
            intent: /dogs
            a: собака block1
        
    state: Block2
        q!: $cat
        a: котик Block2!
        a: котик текст
        a: More test
        a: More test more
        a: mor test more more more
        
    state: Block3
        intent!: /dogs
        a: собака block3
        
    state: CatchAll
        event: noMatch
        a: Вы сказали: {{ $request.query }}
        