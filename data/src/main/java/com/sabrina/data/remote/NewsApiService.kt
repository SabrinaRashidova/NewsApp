package com.sabrina.data.remote

import com.sabrina.data.BuildConfig
import com.sabrina.data.remote.dto.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    suspend fun getTrendingStories(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = BuildConfig.NYT_API_KEY
    ) : NewsResponseDto

    @GET("v2/everything")
    suspend fun searchArticles(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 20,
        @Query("apiKey") apiKey: String = BuildConfig.NYT_API_KEY
    ) : NewsResponseDto
}