package com.kubiakdev.pagingsample.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kubiakdev.pagingsample.api.model.Book
import com.kubiakdev.pagingsample.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(bookRepository: BookRepository) : ViewModel() {

    var dataAlreadyLoaded = false

    // todo handle empty list condition
    val books: Flow<PagingData<Book>> = bookRepository.booksPagerFlow
        .cachedIn(viewModelScope)
        .map { pagingData ->
            pagingData.map {
                dataAlreadyLoaded = true
                it
            }
        }
}