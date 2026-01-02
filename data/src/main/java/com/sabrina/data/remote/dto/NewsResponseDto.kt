package com.sabrina.data.remote.dto

data class NewsResponseDto(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsArticleDto>
)
