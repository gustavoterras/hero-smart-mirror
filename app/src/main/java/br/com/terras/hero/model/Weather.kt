package br.com.terras.hero.model

data class Weather(
        val time: Long,
        val summary: String,
        val icon: String,
        val temperature: Double,
        val temperatureMin: Double,
        val temperatureMax: Double
) {

    fun urlIcon(): String {
        return "https://darksky.net/images/weather-icons/$icon.png"
    }

}

data class Data(
        val summary: String?,
        val icon: String?,
        val data: ArrayList<Weather> = ArrayList()
)