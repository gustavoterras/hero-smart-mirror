package br.com.terras.hero

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.util.Log
import br.com.terras.hero.network.IConsumerService
import br.com.terras.hero.viewmodel.MainViewModel
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
        DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_main)
                .setVariable(BR.viewModel, MainViewModel(this))

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
    }
}
