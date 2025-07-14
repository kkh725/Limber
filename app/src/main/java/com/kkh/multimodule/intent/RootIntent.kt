package com.kkh.multimodule.intent

import com.kkh.multimodule.ui.Reducer
import com.kkh.multimodule.ui.UiEvent
import com.kkh.multimodule.ui.UiState
import com.kkh.multimodule.domain.model.ScreenState
import com.kkh.multimodule.home.HomeRoutes
import com.kkh.multimodule.navigation.BottomNavRoutes
import com.kkh.onboarding.OnBoardingRoute

data class RootState(
    val screenState: ScreenState
) : UiState {
    companion object {
        fun init() = RootState(
            screenState = ScreenState.ONBOARDING_SCREEN
        )
    }
}

sealed class RootEvent : UiEvent {
    data class OnClickedBottomNaviItem(val route: String) : RootEvent()
}

class RootReducer(state: RootState) : Reducer<RootState, RootEvent>(state) {
    override suspend fun reduce(oldState: RootState, event: RootEvent) {
        when (event) {
            is RootEvent.OnClickedBottomNaviItem -> {
                when (event.route) {
                    HomeRoutes.HOME -> {
                        setState(oldState.copy(screenState = ScreenState.HOME_SCREEN))
                    }

                    BottomNavRoutes.TIMER -> {
                        setState(oldState.copy(screenState = ScreenState.TIMER_SCREEN))
                    }

                    BottomNavRoutes.LABORATORY -> {
                        setState(oldState.copy(screenState = ScreenState.LABORATORY_SCREEN))
                    }

                    BottomNavRoutes.MORE -> {
                        setState(oldState.copy(screenState = ScreenState.MORE_SCREEN))
                    }
                }
            }
        }
    }
}