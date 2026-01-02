package com.sabrina.data.remote

import com.sabrina.data.BuildConfig
import com.sabrina.data.remote.dto.NytResponseDto
import com.sabrina.data.remote.dto.SearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NytApiService {
    @GET("svc/mostpopular/v2/viewed/1.json")
    suspend fun getTrendingStories() : NytResponseDto

    @GET("svc/search/v2/articlesearch.json")
    suspend fun searchArticles(
        @Query("q") query: String,
        @Query("page") page: Int,
    ) : SearchResponseDto
}