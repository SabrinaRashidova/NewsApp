package com.sabrina.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sabrina.data.mapper.toDomain
import com.sabrina.data.paging.NewsPagingSource
import com.sabrina.data.remote.NewsApiService
import com.sabrina.domain.model.Article
import com.sabrina.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class NewsRepositoryImpl(
    private val api: NewsApiService
): NewsRepository {
    override suspend fun getTrendingNews(): List<Article> {
        return api.getTrendingStories().articles.map { it.toDomain() }
    }

    override fun getPagedNews(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPagingSource(api,"latest")}
        ).flow
    }
}