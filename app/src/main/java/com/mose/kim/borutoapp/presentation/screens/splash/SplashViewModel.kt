package com.mose.kim.borutoapp.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mose.kim.borutoapp.domain.use_case.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val useCase: UseCase
): ViewModel() {

    private val _onBoardingCompleted = MutableStateFlow(false)
    val onBoardingCompleted: StateFlow<Boolean> = _onBoardingCompleted

    // 실행 시 바로 값 로드
    init {
        viewModelScope.launch(Dispatchers.IO) {
            _onBoardingCompleted.value =        // Flow에서 StateFlow로 변환하기 위해 stateIn 사용
                useCase.readOnBoardingUseCase().stateIn(viewModelScope).value
        }
    }
}