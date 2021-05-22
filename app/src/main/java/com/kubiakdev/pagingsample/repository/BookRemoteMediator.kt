package com.kubiakdev.pagingsample.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.kubiakdev.pagingsample.api.ApiService
import com.kubiakdev.pagingsample.db.AppDatabase
import com.kubiakdev.pagingsample.db.model.BookEntity
import com.kubiakdev.pagingsample.db.model.RemoteKeyEntity
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

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BookItem>
    ): MediatorResult {
        val remoteKeyDao = database.remoteKeyDao()

        return try {
            // get remote key depends on the load type
            var remoteKey: RemoteKeyEntity? = null
            val loadKey: Long = when (loadType) {
                LoadType.REFRESH -> 0L
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    remoteKey = database.withTransaction { remoteKeyDao.getRemoteKeys().lastOrNull() }
                    remoteKey?.nextKey ?: 0L
                }
            }

            // load data for the specific page
            val response = apiService.getBooks(loadKey.toInt() * PAGE_SIZE, PAGE_SIZE)

            // return pagination end if the additional request returns empty list
            if (response.isEmpty()) {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            val bookDao = database.bookDao()
            database.withTransaction {
                // remove all cached data on refresh
                if (loadType == LoadType.REFRESH) {
                    bookDao.clearAll()
                    remoteKeyDao.deleteRemoteKey()
                }

                // save next key and fetched data
                remoteKeyDao.insertOrReplace(
                    remoteKey?.copy(nextKey = remoteKey.nextKey + 1) ?: RemoteKeyEntity(loadKey + 1)
                )
                bookDao.insertAll(response.map {
                    BookEntity(it.id, it.title, it.author, it.description, it.imageInBase64)
                })
            }

            // set last database update to don't send too much requests
            appPreferences.lastDbUpdateInMillis = System.currentTimeMillis()

            // at this point the response didn't have an empty list so it means that there are more data to fetch
            MediatorResult.Success(endOfPaginationReached = false)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)
        // refresh all the data or load the cached data depends on the last time of updating the database
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