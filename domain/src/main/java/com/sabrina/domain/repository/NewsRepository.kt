package com.sabrina.domain.repository

import com.sabrina.domain.model.Article

interface NewsRepository {
    suspend fun getTrendingNews() : List<Article>
}