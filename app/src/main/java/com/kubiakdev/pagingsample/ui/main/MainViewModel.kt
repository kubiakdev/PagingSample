package com.kubiakdev.pagingsample.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.kubiakdev.pagingsample.api.BookPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val bookPagingSource: BookPagingSource
) : ViewModel() {

    val books = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 5)
    ) { bookPagingSource }.flow.cachedIn(viewModelScope)
}