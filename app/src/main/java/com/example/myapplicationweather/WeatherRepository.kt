package com.example.myapplicationweather

class WeatherRepository {
    suspend fun getWeather(city: String, apiKey: String): WeatherResponse {
        return RetrofitInstance.api.getCurrentWeather(city, apiKey)
    }
}
