package com.kubiakdev.pagingsample.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BookResponse(
    @Json(name = "title") val title: String,
    @Json(name = "author") val author: String,
    @Json(name = "description") val description: String,
    @Json(name = "image") val imageInBase64: String
)