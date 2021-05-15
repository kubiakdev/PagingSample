package com.kubiakdev.pagingsample.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kubiakdev.pagingsample.api.model.BookResponse
import com.kubiakdev.pagingsample.ui.main.adapter.item.BookItem
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BookPagingSource @Inject constructor(
    private val bookRepository: BookRepository
) : PagingSource<Int, BookItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookItem> {
        val pageToLoad = params.key ?: 0
        val limit = 5
        return try {
            val response =
                bookRepository.loadBooks(start = pageToLoad * limit, limit)

            LoadResult.Page(
                data = response.map { BookItem(it.title, it.author, it.description, it.imageInBase64) },
                prevKey = null,
                nextKey = if (response.isEmpty()) null else pageToLoad + 1
            )
        } catch (e: IOException) {
            // IOException for network failures.
            LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BookItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
