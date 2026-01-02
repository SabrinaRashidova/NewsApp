package com.sabrina.data.remote.dto

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class SearchArticleDto(
    @SerializedName("web_url") val webUrl: String,
    val snippet: String?,
    val headline: HeadlineDto,
    @SerializedName("pub_date") val pubDate: String?,
    val byline: BylineDto?,
    val multimedia: JsonElement?
)


data class MultimediaImageDto(
    val url: String,
    val subtype: String
)
