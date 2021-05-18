package com.kubiakdev.pagingsample.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.kubiakdev.pagingsample.api.ApiService
import com.kubiakdev.pagingsample.db.AppDatabase
import com.kubiakdev.pagingsample.db.model.BookEntity
import com.kubiakdev.pagingsample.prefs.AppPreferences
import com.kubiakdev.pagingsample.ui.main.adapter.item.BookItem
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class BookRemoteMediator @Inject constructor(
    private val database: AppDatabase,
    private val apiService: ApiService,
    private val appPreferences: AppPreferences
) : RemoteMediator<Int, BookItem>() {

    private var pageToLoad = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BookItem>
    ): MediatorResult {
        return try {
            val response = apiService.getBooks(pageToLoad * PAGE_SIZE, PAGE_SIZE)
            pageToLoad++
            val bookDao = database.bookDao()

            if (response.isEmpty())
                return MediatorResult.Success(endOfPaginationReached = true)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    bookDao.clearAll()
                }

                bookDao.insertAll(response.map {
                    BookEntity(it.id, it.title, it.author, it.description, it.imageInBase64)
                })
            }

            appPreferences.lastDbUpdateInMillis = System.currentTimeMillis()

            MediatorResult.Success(endOfPaginationReached = false)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)
        return if (System.currentTimeMillis() - appPreferences.lastDbUpdateInMillis >= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    companion object {
        const val PAGE_SIZE = 5
    }
}