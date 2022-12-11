package com.mose.kim.borutoapp.data.repository

import androidx.paging.PagingData
import com.mose.kim.borutoapp.domain.model.Hero
import com.mose.kim.borutoapp.domain.repository.DataStoreOperations
import com.mose.kim.borutoapp.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    // hitl가 등록된 module들을 확인하여 같은 데이터타입의 모듈을 DI
    private val remoteDataSource: RemoteDataSource,
    private val dataStore: DataStoreOperations
) {

    fun getAllHeroes(): Flow<PagingData<Hero>> {
        return remoteDataSource.getAllHeroes()
    }

    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.saveOnBoardingState(completed = completed)
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.readOnBoardingState()
    }

}