package br.com.terras.hero.model

data class HeroResponse(
    val currently: Weather?,
    val daily: Data?,
    val offset: Int?
)