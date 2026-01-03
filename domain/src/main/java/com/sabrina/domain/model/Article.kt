package com.sabrina.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey val url: String,
    val title: String,
    val description: String,
    val author: String?,
    val imageUrl: String?,
    val publishedDate: String,
    val isBookmarked: Boolean = false
)
