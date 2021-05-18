package com.kubiakdev.pagingsample.api

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.kubiakdev.pagingsample.api.model.Book
import javax.inject.Inject

class BookRepository @Inject constructor(private val bookPagingSource: BookPagingSource) {

    val booksPagerFlow = Pager(
        PagingConfig(pageSize = 5, prefetchDistance = 5)
    ) { bookPagingSource }.flow
}