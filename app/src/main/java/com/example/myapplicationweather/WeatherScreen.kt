package com.example.myapplicationweather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage // Updated import
import coil3.request.ImageRequest // Updated import
import coil3.request.crossfade

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    var city by remember { mutableStateOf("") }
    val weatherState by viewModel.weather.collectAsState()

    // Background gradient
    val backgroundGradient = if (weatherState == null) {
        Brush.verticalGradient(
            colors = listOf(Color(0xFF9098AE), Color(0xFFB0B8C5))
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(Color(0xFFB0BEC5), Color(0xFF90A4AE))
        )
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(backgroundGradient)
                .padding(16.dp)
        ) {
            // Input card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                color = Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 8.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = city,
                        onValueChange = { city = it },
                        label = { Text("Enter City", color = Color.White) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            val apiKey = BuildConfig.WEATHER_API_KEY
                            viewModel.fetchWeather(city, apiKey)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                    ) {
                        Text("Get Weather", color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Weather info card
            weatherState?.let { data ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    shape = RoundedCornerShape(20.dp),
                    shadowElevation = 8.dp
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        val desc = data.weather[0].description.lowercase()
                        val hour = remember { java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY) }

                        val gifUrl = when {
                            "storm" in desc -> "https://cdn.pixabay.com/animation/2025/03/20/16/33/16-33-20-645_512.gif"
                            "fog" in desc || "haze" in desc -> "https://cdn.pixabay.com/animation/2024/05/27/21/56/21-56-03-220_512.gif"
                            "mist" in desc -> "https://cdn.pixabay.com/animation/2024/05/07/01/58/01-58-15-634_512.gif"
                            "snow" in desc -> "https://media1.tenor.com/m/nsPT4hd45UUAAAAd/traffic-jam-winter.gif"
                            "rain" in desc && hour in 18..23 -> "https://media1.tenor.com/m/FDq3-jxP-vcAAAAC/nature-good-morning-nature.gif"
                            "rain" in desc -> "https://cdn.pixabay.com/animation/2023/02/15/02/20/02-20-04-915_512.gif"
                            "drizzle" in desc -> "https://media1.tenor.com/m/b6iNUZ2UlnMAAAAC/rain.gif"
                            "broken clouds" in desc -> "https://cdn.pixabay.com/animation/2023/03/11/17/29/17-29-27-410_512.gif"
                            "scattered clouds" in desc -> "https://cdn.pixabay.com/animation/2025/07/17/12/49/12-49-50-680_512.gif"
                            "overcast clouds" in desc -> "https://cdn.pixabay.com/animation/2023/02/16/14/40/14-40-49-756_512.gif"
                            "clear sky" in desc && hour in 6..8 -> "https://cdn.pixabay.com/animation/2023/02/20/01/27/01-27-16-323_512.gif"
                            "clear sky" in desc && hour in 9..11 -> "https://media1.tenor.com/m/bL3y1W-D5twAAAAC/what-a-sunny-day.gif"
                            "clear sky" in desc && hour in 12..14 -> "https://media1.tenor.com/m/pypR1LT_dzoAAAAd/walk-walking.gif"
                            "clear sky" in desc && hour in 14..16 -> "https://media1.tenor.com/m/AXST3pQh5r8AAAAd/sunny-day-when-sharks-attack.gif"
                            "clear sky" in desc && hour in 17..19 -> "https://cdn.pixabay.com/animation/2025/01/31/15/41/15-41-48-809_512.gif"
                            "clear sky" in desc && (hour in 20..23 || hour in 0..5) -> "https://cdn.pixabay.com/animation/2023/01/26/04/56/04-56-18-501_512.gif"
                            else -> "https://cdn.pixabay.com/animation/2023/02/15/02/20/02-20-04-915_512.gif"
                        }
// yo chai image load garne code ho.
                        //okay thank you
                        AsyncImage(
                            contentDescription = null,
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(gifUrl)
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(R.drawable.ic_launcher_background),
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(8.dp)),
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                "City: ${data.name}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                "Temperature: ${data.main.temp}°C",
                                fontSize = 18.sp,
                                color = Color.White
                            )
                            Text(
                                "Humidity: ${data.main.humidity}%",
                                fontSize = 18.sp,
                                color = Color.White
                            )
                            Text(
                                "Condition: ${data.weather[0].description}",
                                fontSize = 18.sp,
                                color = Color.White,
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            }
        }
    }
}
