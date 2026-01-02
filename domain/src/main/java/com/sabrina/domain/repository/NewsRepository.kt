package com.sabrina.domain.repository

import androidx.paging.PagingData
import com.sabrina.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getTrendingNews() : List<Article>

    fun getPagedNews() : Flow<PagingData<Article>>
}