package br.com.terras.hero.network

import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Hero Smart Mirror!
 *
 * Created by Gustavo Terras on 27/12/2018.
 * Copyright © 2018. All rights reserved.
 */

interface IConsumerService {

    @GET("v2/top-headlines")
    fun getNews(
        @Query("country") country: String,
        @Query("category") category1: String,
        @Query("category") category2: String,
        @Query("apiKey") apiKey: String
    ): Single<ResponseBody>

    @GET("/forecast/{apiKey}/{lat},{lon}")
    fun getForecast(
        @Path("apiKey") apiKey: String,
        @Path("lat") lat: String,
        @Path("lon") lon: String,
        @Query("lang") lang: String,
        @Query("units") units: String
    ): Single<ResponseBody>

    companion object Factory {
        fun create(baseUrl: String): IConsumerService {

            val httpClient = OkHttpClient.Builder()

            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val retrofit = Retrofit.Builder()
                .client(httpClient.addInterceptor(loggingInterceptor).build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()

            return retrofit.create(IConsumerService::class.java)
        }
    }
}