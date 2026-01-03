package com.sabrina.newsapp.di

import android.content.Context
import androidx.room.Room
import com.sabrina.data.local.ArticleDao
import com.sabrina.data.local.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideArticleDatabase(@ApplicationContext context: Context): ArticleDatabase{
        return Room.databaseBuilder(
            context,
            ArticleDatabase::class.java,
            "news_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideArticleDto(database: ArticleDatabase) : ArticleDao{
        return database.articleDao
    }
}