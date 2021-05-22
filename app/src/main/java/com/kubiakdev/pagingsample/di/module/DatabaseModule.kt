package com.kubiakdev.pagingsample.di.module

import android.content.Context
import androidx.room.Room
import com.kubiakdev.pagingsample.db.AppDatabase
import com.kubiakdev.pagingsample.db.dao.BookDao
import com.kubiakdev.pagingsample.db.dao.RemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    companion object {
        private const val DATABASE_NAME = "database.db"
    }

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()

    @Provides
    fun providesBookDao(db: AppDatabase): BookDao = db.bookDao()

    @Provides
    fun providesRemoteKeyDao(db: AppDatabase): RemoteKeyDao = db.remoteKeyDao()
}