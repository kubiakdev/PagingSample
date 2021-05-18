package com.kubiakdev.pagingsample.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kubiakdev.pagingsample.db.model.BookEntity

@Database(entities = [BookEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao
}