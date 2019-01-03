package br.com.terras.hero.model

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

data class Weather(
        val time: Long,
        val summary: String,
        val icon: String,
        val humidity: Double,
        val temperature: Double,
        val temperatureMin: Double,
        val temperatureMax: Double
) {

    fun urlIcon(): String {
        return "https://darksky.net/images/weather-icons/$icon.png"
    }

    fun date(): String {
        return SimpleDateFormat("EEE", Locale.getDefault()).format(Date(TimeUnit.SECONDS.toMillis(time)))
    }

    fun formatTemperature(temperature: Double): String {
        return "" + temperature.roundToInt() + "˚"
    }

    fun formatHumidity(humidity: Double): String {
        return "" + (humidity * 100).roundToInt() + "%"
    }
}

data class Data(
        val summary: String?,
        val icon: String?,
        val data: ArrayList<Weather> = ArrayList()
)