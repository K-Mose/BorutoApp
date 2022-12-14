package com.mose.kim.borutoapp.domain.repository

import androidx.paging.PagingData
import com.mose.kim.borutoapp.domain.model.Hero
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getAllHeroes(): Flow<PagingData<Hero>>
    fun searchHeroes(): Flow<PagingData<Hero>>
}
