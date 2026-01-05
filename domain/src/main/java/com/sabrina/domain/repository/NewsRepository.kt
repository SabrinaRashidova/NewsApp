package com.sabrina.domain.repository

import androidx.paging.PagingData
import com.sabrina.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getTrendingNews(category: String) : List<Article>

    fun getPagedNews(category: String) : Flow<PagingData<Article>>

    suspend fun upsertArticle(article: Article)

    suspend fun deleteArticle(article: Article)

    fun getSavedArticles(): Flow<List<Article>>

}