package com.kubiakdev.pagingsample.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kubiakdev.pagingsample.db.model.BookEntity

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<BookEntity>)

    @Query("SELECT * FROM ${BookEntity.TABLE_NAME}")
    fun getBooksSource(): PagingSource<Int, BookEntity>

    @Query("DELETE FROM ${BookEntity.TABLE_NAME}")
    suspend fun clearAll()
}