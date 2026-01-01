package com.sabrina.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MediaDto(
    @SerializedName("media-metadata")
    val mediaMetadata: List<MediaMetadataDto>?
)


