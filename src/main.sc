patterns:
    $cat = (кот/котик/котя)

theme: /

    state: start
        q!: * *start
        a: Go!
    
    state:
        q!: asd
        a: asd ok
        
    state: CatchAll
        event: noMatch
        a: Вы сказали: {{ $request.query }}
        
