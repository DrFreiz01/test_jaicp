patterns:
    $cat = (кот/котик/котя)

theme: /

    state: start
        q!: * *start
        a: Go!
    
    state:
        q!: asd
        a: asd ok Test Test Test
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
        
    state: Block3
        intent!: /dogs
        a: собака block3
        
    state: CatchAll
        event: noMatch
        a: Вы сказали: {{ $request.query }}
        
