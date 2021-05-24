package com.kubiakdev.pagingsample.di.module

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.kubiakdev.pagingsample.BuildConfig
import com.kubiakdev.pagingsample.api.ApiService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    private companion object {
        private const val BASE_URL = "http://192.168.1.12:3000/"
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .build()


    @Provides
    @Singleton
    fun providesOkHttpClient(cacheCreator: CacheCreator, @ApplicationContext context: Context): OkHttpClient =
        OkHttpClient.Builder().apply {
            addHttpLoggingInterceptorIfDebug(this, context)
        }
            .cache(cacheCreator.createCache())
            .connectTimeout(3, TimeUnit.SECONDS)
            .build()

    private fun addHttpLoggingInterceptorIfDebug(builder: OkHttpClient.Builder, @ApplicationContext context: Context) {
        builder.addInterceptor(
            ChuckerInterceptor.Builder(context)
                .collector(ChuckerCollector(context))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
        )
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(createHttpLoggingInterceptor())
        }
    }

    private fun createHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()
}