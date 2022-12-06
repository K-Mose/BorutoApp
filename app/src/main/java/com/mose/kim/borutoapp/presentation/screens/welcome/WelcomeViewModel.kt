package com.mose.kim.borutoapp.presentation.screens.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mose.kim.borutoapp.domain.use_case.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// onBoardingState를 화면에 반영하기 위한 viewModel
@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val useCase: UseCase
): ViewModel() {
    fun saveOnBoardingState(completed: Boolean) {
        // saveOnBoardingUseCase -> suspend fun, CoroutineScope에서 실행
        viewModelScope.launch(Dispatchers.IO) {
             useCase.saveOnBoardingUseCase(completed = completed)
        }
    }
}