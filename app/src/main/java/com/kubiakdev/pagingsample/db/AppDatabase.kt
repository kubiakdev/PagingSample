package com.kubiakdev.pagingsample.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kubiakdev.pagingsample.db.dao.BookDao
import com.kubiakdev.pagingsample.db.dao.RemoteKeyDao
import com.kubiakdev.pagingsample.db.model.BookEntity
import com.kubiakdev.pagingsample.db.model.RemoteKeyEntity

@Database(entities = [BookEntity::class, RemoteKeyEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    abstract fun remoteKeyDao(): RemoteKeyDao
}