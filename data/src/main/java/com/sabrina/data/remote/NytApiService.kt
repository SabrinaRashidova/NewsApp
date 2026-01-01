package com.sabrina.data.remote

import com.sabrina.data.remote.dto.NytResponseDto
import retrofit2.http.GET

interface NytApiService {
    @GET("svc/mostpopular/v2/viewed/1.json")
    suspend fun getTrendingStories() : NytResponseDto
}