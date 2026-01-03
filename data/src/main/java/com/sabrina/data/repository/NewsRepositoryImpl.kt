package com.sabrina.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sabrina.data.local.ArticleDao
import com.sabrina.data.local.ArticleDatabase
import com.sabrina.data.mapper.toDomain
import com.sabrina.data.paging.NewsPagingSource
import com.sabrina.data.remote.NewsApiService
import com.sabrina.domain.model.Article
import com.sabrina.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApiService,
    private val articleDao: ArticleDao
): NewsRepository {
    override suspend fun getTrendingNews(): List<Article> {
        return api.getTrendingStories().articles.map { it.toDomain() }
    }

    override fun getPagedNews(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = 100,
                prefetchDistance = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPagingSource(api,"news")}
        ).flow
    }

    override suspend fun upsertArticle(article: Article) = articleDao.upsertArticle(article)

    override suspend fun deleteArticle(article: Article) = articleDao.deleteArticle(article)

    override fun getSavedArticles(): Flow<List<Article>> = articleDao.getAllArticles()
}