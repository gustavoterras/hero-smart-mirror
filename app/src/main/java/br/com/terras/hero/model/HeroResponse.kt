package br.com.terras.hero.model

data class HeroResponse(
    val currently: Weather?,
    val daily: Data?,
    val articles: ArrayList<News> = ArrayList(),
    val offset: Int?,
    val totalResults: Int?
)