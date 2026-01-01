package com.sabrina.data.remote.dto

data class ArticleDto(
    val url: String,
    val title: String,
    val abstract: String,
    val byline: String?,
    val multimedia: List<MultimediaDto>?,
    val published_date: String
)
