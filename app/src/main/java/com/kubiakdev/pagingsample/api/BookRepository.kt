package com.kubiakdev.pagingsample.api

import com.kubiakdev.pagingsample.api.model.Book
import javax.inject.Inject

class BookRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun loadBooks(start: Int, limit: Int): List<Book> = apiService.getBooks(start, limit)
}