package com.mose.kim.borutoapp.di

import android.content.Context
import androidx.room.Room
import com.mose.kim.borutoapp.data.local.BorutoDatabase
import com.mose.kim.borutoapp.util.Constants
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        BorutoDatabase::class.java,
        Constants.BORUTO_DATABASE
    ).build()
}