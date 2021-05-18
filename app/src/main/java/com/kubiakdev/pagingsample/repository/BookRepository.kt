package com.kubiakdev.pagingsample.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.kubiakdev.pagingsample.db.BookDao
import com.kubiakdev.pagingsample.ui.main.adapter.item.BookItem
import javax.inject.Inject

class BookRepository @Inject constructor(
    bookRemoteMediator: BookRemoteMediator,
    private val dao: BookDao
    ) {

    @ExperimentalPagingApi
    val booksPagerFlow = Pager(
        config = PagingConfig(pageSize = BookRemoteMediator.PAGE_SIZE),
        remoteMediator = bookRemoteMediator
    ) {
        dao.getBooksSource() as PagingSource<Int, BookItem>
    }.flow
}