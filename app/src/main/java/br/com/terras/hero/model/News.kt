package br.com.terras.hero.model

import java.util.*

data class News(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: Date
)

data class Source(
    val name: String
)