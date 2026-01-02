package com.sabrina.data.mapper

import com.sabrina.data.remote.dto.ArticleDto
import com.sabrina.data.remote.dto.SearchArticleDto
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


fun SearchArticleDto.toDomain(): Article {
    val baseUrl = "https://www.nytimes.com/"

    val imageUrl = multimedia
        ?.takeIf { it.isJsonArray }
        ?.asJsonArray
        ?.mapNotNull { element ->
            val obj = element.asJsonObject
            val type = obj["type"]?.asString
            val subtype = obj["subtype"]?.asString
            val url = obj["url"]?.asString

            if (type == "image" && url != null) {
                subtype to url
            } else null
        }
        ?.sortedBy { (subtype, _) ->
            when (subtype) {
                "superJumbo" -> 0
                "xlarge" -> 1
                "large" -> 2
                "mediumThreeByTwo440" -> 3
                "thumbnail" -> 4
                else -> 10
            }
        }
        ?.firstOrNull()
        ?.second
        ?.let { baseUrl + it }

    return Article(
        url = webUrl,
        title = headline.main,
        description = snippet ?: "",
        author = byline?.original ?: "Unknown",
        imageUrl = imageUrl,
        publishedDate = pubDate ?: ""
    )
}




