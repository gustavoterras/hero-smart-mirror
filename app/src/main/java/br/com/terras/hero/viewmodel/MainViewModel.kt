package br.com.terras.hero.viewmodel

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import br.com.terras.hero.BR
import br.com.terras.hero.R
import br.com.terras.hero.WEATHER_URL_BASE
import br.com.terras.hero.adapter.RecyclerBindingAdapter
import br.com.terras.hero.configuration.RecyclerViewConfiguration
import br.com.terras.hero.model.Weather
import br.com.terras.hero.network.IConsumerService
import br.com.terras.hero.saveMainThread
import io.reactivex.rxkotlin.subscribeBy

class MainViewModel(val context: Context) {

    var weatherAdapter = RecyclerBindingAdapter<Weather>(R.layout.item_weather, BR.weather, ArrayList<Weather>())

    val recyclerViewConfiguration = RecyclerViewConfiguration().apply {
        layoutManager = LinearLayoutManager(context)
        adapter = weatherAdapter
    }

    init {
        retrieveWeather()
    }

    private fun retrieveWeather() {
        //https://api.darksky.net/forecast/2934201c0367254cba25db728841ceb4/-29.9131791,-51.2088677?&lang=pt&units=auto
        IConsumerService
                .create(WEATHER_URL_BASE)
                .getForecast("2934201c0367254cba25db728841ceb4", "-29.9131791", "-51.2088677", "pt", "auto")
                .saveMainThread()
                .subscribeBy(
                        onSuccess = { response ->
                            weatherAdapter.addAll(response.daily?.data)
                        },
                        onError = {
                            Log.e("TAG", it.message)
                        }
                )
    }

}