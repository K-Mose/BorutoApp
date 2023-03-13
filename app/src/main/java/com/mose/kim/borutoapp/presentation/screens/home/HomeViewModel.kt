package com.mose.kim.borutoapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.mose.kim.borutoapp.domain.use_case.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    useCase: UseCase
) : ViewModel() {
    val getAllHeroes = useCase.getAllHeroesUseCase()
}