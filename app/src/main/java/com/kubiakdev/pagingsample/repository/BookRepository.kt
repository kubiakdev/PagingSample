package com.kubiakdev.pagingsample.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kubiakdev.pagingsample.api.ApiService
import com.kubiakdev.pagingsample.api.model.Book
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookRepository @Inject constructor(private val apiService: ApiService) {

    val booksPagerFlow: Flow<PagingData<Book>> = Pager(
        PagingConfig(pageSize = 5, prefetchDistance = 5, initialLoadSize = 5)
    ) { BookPagingSource(apiService) }.flow
}