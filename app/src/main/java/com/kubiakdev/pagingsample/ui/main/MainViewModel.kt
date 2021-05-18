package com.kubiakdev.pagingsample.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.kubiakdev.pagingsample.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    bookRepository: BookRepository
) : ViewModel() {

    @ExperimentalPagingApi
    val books = bookRepository.booksPagerFlow.cachedIn(viewModelScope)
}