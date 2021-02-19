var Climacell_API_KEY = $injector.api_key;
var MAPBOX_API_KEY = $injector.api_get_coord_key



function get_coord(city){
    return $http.get("https://api.mapbox.com/geocoding/v5/mapbox.places/${City}.json?language=ru&limit=1&access_token=${Token}", {
            timeout: 10000,
            query:{
                City: city,
                Token: MAPBOX_API_KEY
            }
        })

}
function get_city_name(coord){
    return $http.get("https://api.mapbox.com/geocoding/v5/mapbox.places/${lat},${lon}.json?types=place&language=en&limit=1&access_token=${Token}", {
            timeout: 10000,
            query:{
                lat: coord[0],
                lon: coord[1],
                Token: MAPBOX_API_KEY
            }
        })

}


function Climacell(coord, date){

return $http.get("https://api.climacell.co/v3/weather/forecast/daily?lat=${lat}&lon=${lon}&unit_system=si&start_time=${day}&end_time=${day}&fields=temp%2Cweather_code&apikey=${APPID}", {
        timeout: 10000,
        query:{
            lat: coord[1],
            lon: coord[0],
            day: date,
            APPID: Climacell_API_KEY
        }
    });
}

function getRandomInt(max) {
  return Math.floor(Math.random() * Math.floor(max)) | 0;
}

function checkValue(item) {
    if(item.value) {
        return item.value;
    } 
    else {
        return item;
    }
}

function generateMsg(inp,cinp) {
    var rus_unit = {"week": "недели", "day": "дни", "month": "месяцы", "year": "годы"};
    if (!inp.Duration.unit) {
        return "Город: " + inp.City + "\nКоличество людей: " + inp.NumPeople + "\nКоличество детей: " + inp.NumChildren + "\nБюджет: " + inp.Money + "\nДата начала: " + checkValue(inp.StartDate_trip) + "\nДлительность (дни): " + checkValue(inp.Duration) + "\nКоличетсво звёзд у отеля: " + inp.HotelStars + "\n\nИмя: " + cinp.Name + "\nТелефон для связи: " + cinp.Phone + "\nКомментарий: " + inp.Сomment; 
    }
    else {
        return "Город: " + inp.City + "\nКоличество людей: " + inp.NumPeople + "\nКоличество детей: " + inp.NumChildren + "\nБюджет: " + inp.Money + "\nДата начала: " + checkValue(inp.StartDate_trip) + "\nДлительность (" + rus_unit[inp.Duration.unit] + "): " + inp.Duration.value + "\nКоличетсво звёзд у отеля: " + inp.HotelStars + "\n\nИмя: " + cinp.Name + "\nТелефон для связи: " + cinp.Phone + "\nКомментарий: " + inp.Сomment;  
    }
   

    
}

function generateMsg_2(inp) {
    return "Id: " + inp.id + " Comment: " + ПреобразуйСтрокуВЛатиницу(inp.Сomment);
    
}
function weather_state(inp) {
    // координаты
    // $reactions.answer(toPrettyString(inp.StartDate));
    //if (inp.City_weather.substring(1,5) === 'оскв' || inp.City_weather.substring(1,6) === 'адрид') {
    /*if (inp.City_weather.slice(1,-1) === 'гипт') {
        inp.City_weather = "Египет"
    }
    
    if (inp.City_weather.toLowerCase() === 'спб') {
        inp.City_weather = "Санкт-Петербург"
    }*/
    if (inp.City_weather.slice(-1) === 'е') {
        inp.City_weather = inp.City_weather.slice(0,-1)
    }
    if (inp.City_weather.slice(-1) === 'у') {
        inp.City_weather = inp.City_weather.slice(0,-1)
    }
    get_coord(inp.City_weather).then(function(first_answer) {
        if (first_answer && first_answer.features){
            
            inp.City_weather = first_answer.features[0].text
            Climacell(first_answer.features[0].center, inp.StartDate.value).then(function(second_answer) {
            
                if (second_answer) {
                    if (second_answer[1]) 
                    {
                        //$reactions.answer("В "+ inp.City_weather + " " + inp.StartDate.day + " " + $translation["2"].value[inp.StartDate.month - 1] + " будет от " + Math.round(second_answer[1].temp[0].min.value) + "˚ C до "+ Math.round(second_answer[1].temp[1].max.value) +"˚ C " + $translation["1"].value[second_answer[0].weather_code.value])
                        var mean_temp = Math.round(0.5 * (second_answer[1].temp[0].min.value + second_answer[1].temp[1].max.value));
                        $reactions.answer("В локации "+ inp.City_weather + " " + inp.StartDate.day + " " + trans.MONTH[inp.StartDate.month - 1] + " будет средняя температура воздуха " + mean_temp +"˚ C, " + trans.WEATHER_DICT[second_answer[1].weather_code.value])
                        if(mean_temp < 5){
                            $reactions.answer("Вы хотите поехать в страну с холодным климатом?" );
                        }
                        else if (mean_temp >= 5 && mean_temp < 28){
                            $reactions.answer("Вы хотите поехать в страну с умеренным климатом?" );
                        }
                        else{
                            $reactions.answer("Вы хотите поехать в страну с жарким климатом?" );
                        }
                    }
                
                    else 
                    {   
                        if (second_answer[0])
                        {
                            var mean_temp = Math.round(0.5 * (second_answer[0].temp[0].min.value + second_answer[0].temp[1].max.value));
                            $reactions.answer("В локации " + inp.City_weather + " "+inp.StartDate.day + " " + trans.MONTH[inp.StartDate.month - 1] + " будет средняя температура воздуха " + mean_temp +"˚ C, " + trans.WEATHER_DICT[second_answer[0].weather_code.value]);
                            
                            if(mean_temp < 5)
                            {
                                $reactions.answer("Вы хотите поехать в страну с холодным климатом?" );
                            }
                            else if (mean_temp >= 5 && mean_temp < 28){
                            $reactions.answer("Вы хотите поехать в страну с умеренным климатом?" );
                            }
                            else{
                                $reactions.answer("Вы хотите поехать в страну с жарким климатом?" );
                            }
                        }
                        else 
                        {
                            $reactions.answer("Что-то сервер барахлит. Не могу узнать погоду. Попробуйте указать другую страну.");
                        }
                    }
                }
                else {
                    $reactions.answer("Что-то сервер барахлит. Не могу узнать погоду. Попробуйте указать другую страну.");
                }}).catch(function (err) {
                   $reactions.answer("Что-то сервер барахлит. Не могу узнать погоду. Попробуйте указать другую страну.");
                                // $reactions.answer(toPrettyString(err));
                });
        }
        else {
            $reactions.answer("Что-то сервер барахлит. Не могу узнать погоду. Попробуйте указать другую страну.");
        }}).catch(function (err) {
            $reactions.answer("Что-то сервер барахлит. Не могу узнать погоду. Попробуйте указать другую страну.");
        });
}


function customSendEmail(inp, cinp) {
    
    inp.result = $http.post(
        "https://api.sendgrid.com/v3/mail/send",
        {
            dataType: "json",
            headers:
            {
                "Authorization": "Bearer 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",
                "Content-Type": "application/json"
            },
            body: 
                { "personalizations": 
                [
                    { "to": [{ "email": "volkoshkursk-98@yandex.ru" }, { "email": "liza301199@yandex.ru" }/*{"email": "inviz.ize@gmail.com"}*/] }
                ], 
                "from": 
                {
                    "email": "volkoshkursk-98@yandex.ru"
                },
                "subject": "Заявка на тур №" + getRandomInt(1000000),
                "content": 
                    [
                        {
                            "type": "text/plain",
                            "value": generateMsg(inp,cinp)
                        }
                    ]
    }});                                 

                    
    if (inp.result.isOk) {
                            
        $reactions.answer("Ваша заявка отправлена. В ближайшее время с Вами свяжется менеджер, чтобы уточнить детали.");
                                
    }
    else 
    {
        $reactions.answer("При отправке письма возникли ошибки. Позвоните по телефону 8(812)000-00-00, чтобы мы подобрали для Вас тур.");
    }
    
}