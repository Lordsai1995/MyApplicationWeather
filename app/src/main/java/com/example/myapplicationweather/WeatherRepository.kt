package com.example.myapplicationweather

class WeatherRepository {
    suspend fun getWeather(city: String, apiKey: String): WeatherResponse {
        return RetrofitInstance.api.getCurrentWeather(city, apiKey)
    }

    suspend fun getWeather(latitude: String, longitude: String, apiKey: String): WeatherResponse {
        return RetrofitInstance.api.getCurrentWeatherByCoordinates(
            latitude = latitude,
            longitude = longitude,
            apiKey = apiKey
        )
    }
}
