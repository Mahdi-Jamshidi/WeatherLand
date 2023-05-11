package ir.magiccodes.weatherland.model.data


import com.google.gson.annotations.SerializedName

data class WeatherData(
    @SerializedName("current")
    val current: Current,
    @SerializedName("forecast")
    val forecast: Forecast,
    @SerializedName("location")
    val location: Location
) {
    data class Current(
        @SerializedName("air_quality")
        val airQuality: AirQuality,
        @SerializedName("cloud")
        val cloud: Int,
        @SerializedName("condition")
        val condition: Condition,
        @SerializedName("feelslike_c")
        val feelslikeC: Double,
        @SerializedName("gust_kph")
        val gustKph: Double,
        @SerializedName("humidity")
        val humidity: Int,
        @SerializedName("is_day")
        val isDay: Int,
        @SerializedName("last_updated")
        val lastUpdated: String,
        @SerializedName("last_updated_epoch")
        val lastUpdatedEpoch: Int,
        @SerializedName("precip_mm")
        val precipMm: Double,
        @SerializedName("temp_c")
        val tempC: Double,
        @SerializedName("uv")
        val uv: Double,
        @SerializedName("vis_km")
        val visKm: Double,
        @SerializedName("wind_degree")
        val windDegree: Int,
        @SerializedName("wind_dir")
        val windDir: String,
        @SerializedName("wind_kph")
        val windKph: Double
    ) {
        data class AirQuality(
            @SerializedName("co")
            val co: Double,
            @SerializedName("gb-defra-index")
            val gbDefraIndex: Int,
            @SerializedName("no2")
            val no2: Double,
            @SerializedName("o3")
            val o3: Double,
            @SerializedName("pm10")
            val pm10: Double,
            @SerializedName("pm2_5")
            val pm25: Double,
            @SerializedName("so2")
            val so2: Double,
            @SerializedName("us-epa-index")
            val usEpaIndex: Int
        )

        data class Condition(
            @SerializedName("code")
            val code: Int,
            @SerializedName("icon")
            val icon: String,
            @SerializedName("text")
            val text: String
        )
    }

    data class Forecast(
        @SerializedName("forecastday")
        val forecastday: List<Forecastday>
    ) {
        data class Forecastday(
            @SerializedName("astro")
            val astro: Astro,
            @SerializedName("date")
            val date: String,
            @SerializedName("date_epoch")
            val dateEpoch: Int,
            @SerializedName("day")
            val day: Day,
            @SerializedName("hour")
            val hour: List<Hour>
        ) {
            data class Astro(
                @SerializedName("is_moon_up")
                val isMoonUp: Int,
                @SerializedName("is_sun_up")
                val isSunUp: Int
            )

            data class Day(
                @SerializedName("avgtemp_c")
                val avgtempC: Double,
                @SerializedName("condition")
                val condition: Condition,
                @SerializedName("daily_chance_of_rain")
                val dailyChanceOfRain: Int,
                @SerializedName("daily_chance_of_snow")
                val dailyChanceOfSnow: Int,
                @SerializedName("maxtemp_c")
                val maxtempC: Double,
                @SerializedName("mintemp_c")
                val mintempC: Double,
                @SerializedName("totalsnow_cm")
                val totalsnowCm: Double
            ) {
                data class Condition(
                    @SerializedName("code")
                    val code: Int,
                    @SerializedName("icon")
                    val icon: String,
                    @SerializedName("text")
                    val text: String
                )
            }

            data class Hour(
                @SerializedName("chance_of_rain")
                val chanceOfRain: Int,
                @SerializedName("chance_of_snow")
                val chanceOfSnow: Int,
                @SerializedName("cloud")
                val cloud: Int,
                @SerializedName("condition")
                val condition: Condition,
                @SerializedName("dewpoint_c")
                val dewpointC: Double,
                @SerializedName("feelslike_c")
                val feelslikeC: Double,
                @SerializedName("gust_kph")
                val gustKph: Double,
                @SerializedName("humidity")
                val humidity: Int,
                @SerializedName("is_day")
                val isDay: Int,
                @SerializedName("precip_mm")
                val precipMm: Double,
                @SerializedName("temp_c")
                val tempC: Double,
                @SerializedName("time")
                val time: String,
                @SerializedName("time_epoch")
                val timeEpoch: Int,
                @SerializedName("uv")
                val uv: Double,
                @SerializedName("vis_km")
                val visKm: Double,
                @SerializedName("wind_degree")
                val windDegree: Int,
                @SerializedName("wind_dir")
                val windDir: String,
                @SerializedName("wind_kph")
                val windKph: Double
            ) {
                data class Condition(
                    @SerializedName("code")
                    val code: Int,
                    @SerializedName("icon")
                    val icon: String,
                    @SerializedName("text")
                    val text: String
                )
            }
        }
    }

    data class Location(
        @SerializedName("country")
        val country: String,
        @SerializedName("lat")
        val lat: Double,
        @SerializedName("localtime")
        val localtime: String,
        @SerializedName("localtime_epoch")
        val localtimeEpoch: Int,
        @SerializedName("lon")
        val lon: Double,
        @SerializedName("name")
        val name: String,
        @SerializedName("region")
        val region: String,
        @SerializedName("tz_id")
        val tzId: String
    )
}