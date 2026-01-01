package com.sabrina.data.repository

import com.sabrina.data.mapper.toDomain
import com.sabrina.data.remote.NytApiService
import com.sabrina.domain.model.Article
import com.sabrina.domain.repository.NewsRepository

class NewsRepositoryImpl(
    private val api: NytApiService
): NewsRepository {
    override suspend fun getTrendingNews(): List<Article> {
        return api.getTrendingStories().results.map { it.toDomain() }
    }
}