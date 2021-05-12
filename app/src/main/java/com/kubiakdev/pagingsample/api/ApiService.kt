package com.kubiakdev.pagingsample.api

import com.kubiakdev.pagingsample.api.model.Book
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/books")
    suspend fun getBooks(
        @Query(value = "start") start: Int,
        @Query(value = "limit") limit: Int
    ): List<Book>
}