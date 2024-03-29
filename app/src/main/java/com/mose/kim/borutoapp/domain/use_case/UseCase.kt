package com.mose.kim.borutoapp.domain.use_case

import com.mose.kim.borutoapp.domain.use_case.get_all_heroes.GetAllHeroesUseCase
import com.mose.kim.borutoapp.domain.use_case.read_onboarding.ReadOnBoardingUseCase
import com.mose.kim.borutoapp.domain.use_case.save_onboarding.SaveOnBoardingUseCase

data class UseCase (
    val getAllHeroesUseCase: GetAllHeroesUseCase,
    val saveOnBoardingUseCase: SaveOnBoardingUseCase,
    val readOnBoardingUseCase: ReadOnBoardingUseCase,
)