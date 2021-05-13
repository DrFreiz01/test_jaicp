// function to make a http request for weather forecast service
function getCurrentWeather(city, diff) {
	var apiKey = $jsapi.context().injector.weatherApiKey;
	var response = $http.get("http://api.openweathermap.org/data/2.5/forecast/daily?q=${city}&APPID=${APPID}&units=${units}&lang=${lang}&cnt=16", {
            timeout: 10000,
            query:{
                APPID: apiKey,
                units: "metric",
                city: city,
                lang: "ru"
            }
        });
	var weather = {};
    if (response.isOk) {
        weather.temperature = Math.floor(response.data.list[diff].temp.day);
        weather.description = response.data.list[diff].weather[0].description;
    }
    
    return weather;

}

// check that client's date is in 15 days interval 
function getDateDiff(date) {
    var diff = moment(date).diff(currentDate(), 'days');

    return diff;
    
}

// function returning city name in the locative case with preposition 
function inflectCity(cityName) {
    var preposition = (cityName.startsWith("В") || cityName.startsWith("Ф")) && !/[ауоыиэяюёе]/.test(cityName[1]) ? "Во" : "В";
    var cityNameLoct = cityName.split(" ").map(function(word) { return capitalize($nlp.inflect(word, "loct")); }).join(" ");
    
    return preposition + " " + cityNameLoct;

}
