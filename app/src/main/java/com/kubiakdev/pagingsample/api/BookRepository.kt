package com.kubiakdev.pagingsample.api

import com.kubiakdev.pagingsample.api.model.BookResponse
import javax.inject.Inject

class BookRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun loadBooks(start: Int, limit: Int): List<BookResponse> = apiService.getBooks(start, limit)
}