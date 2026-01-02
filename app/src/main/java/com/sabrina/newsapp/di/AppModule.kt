package com.sabrina.newsapp.di

import com.sabrina.data.remote.NewsApiService
import com.sabrina.data.repository.NewsRepositoryImpl
import com.sabrina.domain.repository.NewsRepository
import com.sabrina.newsapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient{
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val original = chain.request()
                val url = original.url.newBuilder()
                    .addQueryParameter("api-key", BuildConfig.NYT_API_KEY)
                    .build()
                chain.proceed(original.newBuilder().url(url).build())
            }.build()
    }

    @Provides
    @Singleton
    fun provideNytApi(client: OkHttpClient): NewsApiService {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(NewsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(api: NewsApiService) : NewsRepository{
        return NewsRepositoryImpl(api)
    }
}