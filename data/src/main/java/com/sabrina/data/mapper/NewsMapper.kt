package com.sabrina.data.mapper

import com.sabrina.data.remote.dto.ArticleDto
import com.sabrina.domain.model.Article

fun ArticleDto.toDomain(): Article{
    val imageUrl = media?.firstOrNull()?.mediaMetadata?.lastOrNull()?.url

    return Article(
        url = url,
        title = title,
        description = abstract,
        author = byline,
        imageUrl = imageUrl,
        publishedDate = published_date
    )
}