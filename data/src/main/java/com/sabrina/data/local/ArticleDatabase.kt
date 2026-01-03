package com.sabrina.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sabrina.domain.model.Article

@Database(entities = [Article::class], version = 1)
abstract class ArticleDatabase : RoomDatabase(){
    abstract val articleDao: ArticleDao
}