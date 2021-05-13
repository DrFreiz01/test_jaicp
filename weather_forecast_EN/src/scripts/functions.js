// function to make a http request for weather forecast service
function getCurrentWeather(city, diff) {
	var apiKey = $jsapi.context().injector.weatherApiKey;
	var response = $http.get("http://api.openweathermap.org/data/2.5/forecast/daily?q=${city}&APPID=${APPID}&units=${units}&lang=${lang}&cnt=16", {
            timeout: 10000,
            query:{
                APPID: apiKey,
                units: "metric",
                city: city,
                lang: "en"
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
