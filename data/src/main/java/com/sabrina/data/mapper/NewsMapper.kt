package com.sabrina.data.mapper

import com.sabrina.data.remote.dto.ArticleDto
import com.sabrina.domain.model.Article

fun ArticleDto.toDomain(): Article{
    return Article(
        url = url,
        title = title,
        description = abstract,
        author = byline,
        imageUrl = multimedia?.firstOrNull{it.format == "superJumbo"}?.url,
        publishedDate = published_date
    )
}