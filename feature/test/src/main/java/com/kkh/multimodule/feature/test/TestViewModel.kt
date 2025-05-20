package com.kkh.multimodule.feature.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkh.multimodule.domain.repository.TestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TestViewModel @Inject constructor(private val testRepository: TestRepository) : ViewModel() {

    private val reducer = TestReducer(TestState.init())
    val uiState get() = reducer.uiState

    fun sendEvent(e: TestEvent) {
        when (e) {
            TestEvent.ClickedButton -> {
                testFunction()
            }
        }
    }

    private fun testFunction() {
        viewModelScope.launch {
            testRepository.localDoit()
        }
    }
}