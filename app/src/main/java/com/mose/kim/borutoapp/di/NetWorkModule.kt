package com.mose.kim.borutoapp.di

import androidx.compose.ui.unit.Constraints
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mose.kim.borutoapp.data.remote.BorutoApi
import com.mose.kim.borutoapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {

    // OkHttpClient
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.MINUTES)
            .connectTimeout(15, TimeUnit.MINUTES)
            .build()
    }

    // Retrofit
    @Singleton
    @Provides // okHttp from provideHttpClient
    fun providerNetWork(okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory(contentType = contentType))
//            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // describe how to provide api
    @Provides
    @Singleton // retrofit form providerNetWork
    fun provideBorutoApi(retrofit: Retrofit): BorutoApi {
        return retrofit.create(BorutoApi::class.java)
    }

}