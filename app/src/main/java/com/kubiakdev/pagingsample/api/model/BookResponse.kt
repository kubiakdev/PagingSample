package com.kubiakdev.pagingsample.api.model

import com.kubiakdev.pagingsample.ui.main.adapter.item.BookItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BookResponse(
    @Json(name = "_id") override val id: String,
    @Json(name = "title") override val title: String,
    @Json(name = "author") override val author: String,
    @Json(name = "description") override val description: String,
    @Json(name = "image") override val imageInBase64: String
): BookItem