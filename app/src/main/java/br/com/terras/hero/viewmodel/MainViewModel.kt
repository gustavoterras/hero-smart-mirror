package br.com.terras.hero.viewmodel

import android.content.Context
import android.databinding.ObservableField
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import br.com.terras.hero.*
import br.com.terras.hero.adapter.RecyclerBindingAdapter
import br.com.terras.hero.configuration.RecyclerViewConfiguration
import br.com.terras.hero.model.News
import br.com.terras.hero.model.Weather
import br.com.terras.hero.network.IConsumerService
import io.reactivex.rxkotlin.subscribeBy
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(private val context: Context) {

    private var weatherAdapter = RecyclerBindingAdapter<Weather>(R.layout.item_weather, BR.weather, ArrayList<Weather>())
    var currentlyWeather: ObservableField<Weather> = ObservableField()

    val weatherRVConfiguration = RecyclerViewConfiguration().apply {
        layoutManager = LinearLayoutManager(context)
        adapter = weatherAdapter
    }

    private var newsAdapter = RecyclerBindingAdapter<News>(R.layout.item_news, BR.news, ArrayList<News>())

    val newsRVConfiguration = RecyclerViewConfiguration().apply {
        layoutManager = LinearLayoutManager(context)
        adapter = newsAdapter
    }

    init {
        retrieveWeather()
        retrieveNews()
    }

    private fun retrieveWeather() {
        //https://api.darksky.net/forecast/2934201c0367254cba25db728841ceb4/-29.9131791,-51.2088677?&lang=pt&units=auto
        IConsumerService
            .create(WEATHER_URL_BASE)
            .getForecast("2934201c0367254cba25db728841ceb4", "-29.9131791", "-51.2088677", "pt", "auto")
            .saveMainThread()
            .subscribeBy(
                onSuccess = { response ->
                    currentlyWeather.set(response.currently)
                    weatherAdapter.addAll(response.daily?.data?.let { it.subList(1, it.size) })
                },
                onError = {
                    Log.e("TAG", it.message)
                }
            )
    }

    private fun retrieveNews() {
        //https://newsapi.org/v2/top-headlines?country=br&category=technology&category=businnes&pageSize=3&apiKey=51c3f7779ef94707a83682d131d0e48d
        IConsumerService
            .create(NEWS_URL_BASE)
            .getNews("br", "technology", "businnes", 3, "51c3f7779ef94707a83682d131d0e48d")
            .saveMainThread()
            .subscribeBy(
                onSuccess = { response ->
                    newsAdapter.addAll(response.articles)
                },
                onError = {}
            )
    }

    fun formatFullDate(): String{
        return SimpleDateFormat("EEEE, dd MMMM, yyyy", Locale.getDefault()).format(Date())
    }

    fun formatHour(): String{
        return SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(Date())
    }

}