package br.com.terras.hero

import android.app.Activity
import android.os.Bundle
import android.util.Log
import br.com.terras.hero.network.IConsumerService
import io.reactivex.rxkotlin.subscribeBy

/**
 * Hero Smart Mirror!
 *
 * Created by Gustavo Terras on 27/12/2018.
 * Copyright © 2018. All rights reserved.
 */

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //https://newsapi.org/v2/top-headlines?country=br&category=technology&category=businnes&apiKey=51c3f7779ef94707a83682d131d0e48d
        IConsumerService
            .create(NEWS_URL_BASE)
            .getNews("br", "technology", "businnes", "51c3f7779ef94707a83682d131d0e48d")
            .saveMainThread()
            .subscribeBy(
                onSuccess = { responseBody ->
                    Log.e("TAG", responseBody.string())
                },
                onError = {}
            )

        //https://api.darksky.net/forecast/2934201c0367254cba25db728841ceb4/-29.9131791,-51.2088677?&lang=pt&units=auto
        IConsumerService
            .create(WEATHER_URL_BASE)
            .getForecast("2934201c0367254cba25db728841ceb4", "-29.9131791", "-51.2088677", "pt", "auto")
            .saveMainThread()
            .subscribeBy(
                onSuccess = { responseBody ->
                    Log.e("TAG", responseBody.string())
                },
                onError = {}
            )
    }
}
