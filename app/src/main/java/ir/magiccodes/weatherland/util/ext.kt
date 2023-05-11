package ir.magiccodes.weatherland.util

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import ir.magiccodes.weatherland.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

fun conditionImage(imageId: Int, isDay: Boolean): Int {

    var image = R.drawable.img_big_snow

    if (isDay) {
        when (imageId) {
            113 -> {
                image = R.drawable.img_sun
            }
            308, 263, 266, 281, 284, 296, 302, 311, 314, 317, 320, 185 -> {
                image = R.drawable.img_big_rain_drops
            }
            199, 122, 143, 248, 260 -> {
                image = R.drawable.img_cloudy
            }
            116 -> {
                image = R.drawable.img_sun_cloud
            }
            176, 182, 293, 299, 253, 362, 265 -> {
                image = R.drawable.img_sun_cloud_little_rain
            }
            227, 230 -> {
                image = R.drawable.img_mid_snow_fast_winds
            }
            179, 323, 326, 329, 332, 335, 338, 350, 368, 371, 374, 377 -> {
                image = R.drawable.img_big_snow
            }
            200, 386, 392, 395 -> {
                image = R.drawable.img_cloud_zap
            }
            389 -> {
                image = R.drawable.img_zaps
            }

            305, 356, 359 -> {
                image = R.drawable.img_sun_cloud_mid_rain
            }
        }
    } else {
        when (imageId) {
            113 -> {
                image = R.drawable.img_moon_fast_wind
            }
            263, 266, 296, 302, 308, 317, 320, 185, 281, 284, 311, 314 -> {
                image = R.drawable.img_big_rain_drops
            }
            179, 227, 230, 323, 326, 329, 332, 335, 338, 350, 365, 368, 371, 374, 377 -> {
                image = R.drawable.img_big_snow
            }
            200, 386, 389, 392, 395 -> {
                image = R.drawable.img_cloud_zap
            }
            122, 143, 248, 260 -> {
                image = R.drawable.img_cloudy
            }
            116, 119 -> {
                image = R.drawable.img_moon_cloud_fast_wind
            }
            176, 182, 293, 305, 353, 356, 359, 362 -> {
                image = R.drawable.img_moon_cloud_mid_rain
            }
        }
    }

    return image
}

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
fun convertDateToHour(date: String): String {
    // convert date from yyyy-MM-dd HH:mm to HH
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val dateTime = LocalDateTime.parse(date, formatter)
    var hour = dateTime.hour

    // convert hour from 24hr to 12hr
    return convertFrom24hrTo12hr(hour)
}

@RequiresApi(Build.VERSION_CODES.O)
fun convertDateToDayOfWeek(time: String): String {

    val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = LocalDate.parse(time, firstApiFormat)

    val dayOfWeek = date.dayOfWeek.toString()
    return dayOfWeek.removeRange(3, dayOfWeek.length)

}

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentHour(): String {
    val now = LocalDateTime.now()
    var hour = now.hour
    return convertFrom24hrTo12hr(hour)
}

fun convertFrom24hrTo12hr(time: Int): String {
    // convert hour from 24hr to 12hr
    var hour = time
    var suffix = ""
    if (hour > 11) {
        suffix = "PM"
        if (hour > 12)
            hour -= 12
    } else {
        suffix = "AM"
        if (hour == 0)
            hour = 12
    }

    return "$hour $suffix"
}

fun descriptionOfAirQuality(index: Int): String {
    var description = ""
    when (index) {
        in 0..50 -> {
            description = "$index-Air quality is satisfactory"
        }
        in 51..100 -> {
            description = "$index-Air quality is acceptable"
        }
        in 101..150 -> {
            description = "$index-Unhealthy for Sensitive Groups"
        }
        in 151..200 -> {
            description = "$index-Air quality is Unhealthy"
        }
        in 201..300 -> {
            description = "$index-Air quality is Very Unhealthy"
        }
        in 301..500 -> {
            description = "$index-High Health Risk"
        }
    }

    return description
}

fun descriptionOfUvIndex(index: Int): String {
    var description = ""
    when(index){
        1->{description = "Good"}
        2->{description = "Moderate"}
        3->{description = "Unhealthy for sensitive group"}
        4->{description = "Unhealthy"}
        5->{description = "Very Unhealthy"}
        6->{description = "Hazardous"}
    }

    return description
}