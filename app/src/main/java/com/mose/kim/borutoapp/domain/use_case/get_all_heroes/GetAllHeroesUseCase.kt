package com.mose.kim.borutoapp.domain.use_case.get_all_heroes

import androidx.paging.PagingData
import com.mose.kim.borutoapp.data.repository.Repository
import com.mose.kim.borutoapp.domain.model.Hero
import kotlinx.coroutines.flow.Flow

class GetAllHeroesUseCase(
    private val repository: Repository
){
    operator fun invoke(): Flow<PagingData<Hero>> {
        return repository.getAllHeroes()
    }
}