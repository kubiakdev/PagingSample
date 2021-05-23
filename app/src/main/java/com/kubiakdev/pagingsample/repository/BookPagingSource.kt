package com.kubiakdev.pagingsample.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kubiakdev.pagingsample.api.ApiService
import com.kubiakdev.pagingsample.api.model.Book
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BookPagingSource @Inject constructor(
    private val apiService: ApiService
) : PagingSource<Int, Book>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        val pageToLoad = params.key ?: 0
        return try {
            val response = apiService.getBooks(start = pageToLoad * params.loadSize, params.loadSize)

            LoadResult.Page(
                data = response,
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

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
