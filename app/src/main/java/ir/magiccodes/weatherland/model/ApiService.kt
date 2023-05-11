package ir.magiccodes.weatherland.model

import io.reactivex.Single
import ir.magiccodes.weatherland.model.data.WeatherData
import retrofit2.http.GET

interface ApiService {

    @GET("forecast.json?key=17a426e0218f458aa51124647230102&q=London&days=7&aqi=yes&alerts=no")
    fun getWeather(): Single<WeatherData>
}

