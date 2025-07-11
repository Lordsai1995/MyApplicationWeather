package com.example.myapplicationweather

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    var city by remember { mutableStateOf("") }
    val weatherState by viewModel.weather.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Enter City") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val apiKey = BuildConfig.WEATHER_API_KEY
            viewModel.fetchWeather(city, apiKey)
        }) {
            Text("Get Weather")
        }

        Spacer(modifier = Modifier.height(16.dp))

        weatherState?.let { data ->
            Text("City: ${data.name}")
            Text("Temperature: ${data.main.temp}°C")
            Text("Humidity: ${data.main.humidity}%")
            Text("Condition: ${data.weather[0].description}")
        }
    }
}
