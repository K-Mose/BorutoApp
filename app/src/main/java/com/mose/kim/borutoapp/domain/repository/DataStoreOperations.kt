package com.mose.kim.borutoapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    suspend fun saveOnBoardingState(completed: Boolean) // 완료 버튼 눌렀을 때 값 저장
    fun readOnBoardingState(): Flow<Boolean> // 앱 실행 시 비동기로 읽어들이기 위해
}