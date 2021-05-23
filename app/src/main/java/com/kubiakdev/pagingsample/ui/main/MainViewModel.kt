package com.kubiakdev.pagingsample.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kubiakdev.pagingsample.repository.BookRepository
import com.kubiakdev.pagingsample.api.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(bookRepository: BookRepository) : ViewModel() {

    val books: Flow<PagingData<Book>> = bookRepository.booksPagerFlow.cachedIn(viewModelScope)
}