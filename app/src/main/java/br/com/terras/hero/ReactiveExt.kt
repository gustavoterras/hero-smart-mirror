package br.com.terras.hero

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Hero Smart Mirror!
 *
 * Created by Gustavo Terras on 27/12/2018.
 * Copyright © 2018. All rights reserved.
 */

//region --- Single ---
private fun <T> Single<T>.subscribeOnIo(): Single<T> = subscribeOn(Schedulers.io())
private fun <T> Single<T>.observeOnMainThread(): Single<T> = observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.saveMainThread(): Single<T> = subscribeOnIo().observeOnMainThread()
//endregion