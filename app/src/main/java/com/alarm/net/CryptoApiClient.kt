package com.alarm.net

import android.app.Application
import android.content.Context
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object CryptoApiClient {
    private const val BASE_URL = "https://api.coingecko.com/"
    private const val CACHE_SIZE = 10 * 1024 * 1024 // 10 MB

    private val cacheInterceptor = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        val cacheControl = CacheControl.Builder()
            .maxAge(2, TimeUnit.MINUTES) // Cache de 2 minutos
            .build()
        response.newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .build()
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    fun create(application: Application): CoinGeckoApiService {
        return createWithContext(application.applicationContext)
    }

    private fun createWithContext(context: Context): CoinGeckoApiService {
        val cache = Cache(File(context.cacheDir, "crypto_cache"), CACHE_SIZE.toLong())

        val cacheInterceptor = Interceptor { chain ->
            val response = chain.proceed(chain.request())
            val cacheControl = CacheControl.Builder()
                .maxAge(2, TimeUnit.MINUTES)
                .build()
            response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build()
        }

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val okHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(cacheInterceptor)
            .addNetworkInterceptor(cacheInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(CoinGeckoApiService::class.java)
    }
}