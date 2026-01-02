package com.sabrina.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sabrina.data.BuildConfig
import com.sabrina.data.mapper.toDomain
import com.sabrina.data.remote.NytApiService
import com.sabrina.domain.model.Article

class NewsPagingSource(
    private val apiService: NytApiService,
    private val query: String
) : PagingSource<Int, Article>(){

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: 0

        return try {
            val response = apiService.searchArticles(
                query = query,
                page = position,
            )
            val articles = response.response.docs.map { it.toDomain() }

            LoadResult.Page(
                data = articles,
                prevKey = if (position == 0) null else position-1,
                nextKey = if (articles.isEmpty()) null else position+1
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}