package com.sabrina.domain.model

data class Article(
    val url: String,
    val title: String,
    val description: String,
    val author: String?,
    val imageUrl: String?,
    val publishedDate: String
)
