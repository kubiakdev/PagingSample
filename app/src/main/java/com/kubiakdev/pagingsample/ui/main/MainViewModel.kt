package com.kubiakdev.pagingsample.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kubiakdev.pagingsample.api.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(bookRepository: BookRepository) : ViewModel() {

    val books = bookRepository.booksPagerFlow.cachedIn(viewModelScope)
}