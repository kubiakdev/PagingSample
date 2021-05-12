package com.kubiakdev.pagingsample.di.module

import android.app.Application
import okhttp3.Cache
import java.io.File
import javax.inject.Inject

class CacheCreator @Inject constructor(private val application: Application) {

    private companion object {
        private const val CACHE_FILE_NAME = "api-cache"
        private const val CACHE_FILE_SIZE = 1 * 1024 * 1024L // 1 MiB
    }

    fun createCache(): Cache {
        val cacheFile = File(application.cacheDir, CACHE_FILE_NAME)
        return Cache(cacheFile, CACHE_FILE_SIZE)
    }
}