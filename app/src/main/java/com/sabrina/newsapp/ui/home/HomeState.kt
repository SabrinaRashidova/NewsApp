package com.sabrina.newsapp.ui.home

import com.sabrina.domain.model.Article

data class HomeState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val error: String? = null,
    val selectedCategory: String = "General"
)
