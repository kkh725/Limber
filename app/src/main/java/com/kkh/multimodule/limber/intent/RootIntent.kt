package com.kkh.multimodule.limber.intent

import com.kkh.multimodule.core.ui.ui.Reducer
import com.kkh.multimodule.core.ui.ui.UiEvent
import com.kkh.multimodule.core.ui.ui.UiState
import com.kkh.multimodule.core.domain.ScreenState
import com.kkh.multimodule.feature.home.HomeRoutes
import com.kkh.multimodule.feature.laboratory.LaboratoryRoutes
import com.kkh.multimodule.feature.timer.TimerRoute
import com.kkh.multimodule.limber.navigation.BottomNavRoutes

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
    data class SetScreenState(val screenState: ScreenState) : RootEvent()
    data class OnClickedBottomNaviItem(val route: String) : RootEvent()
}

class RootReducer(state: RootState) : Reducer<RootState, RootEvent>(state) {
    override suspend fun reduce(oldState: RootState, event: RootEvent) {
        when (event) {
            is RootEvent.SetScreenState -> {
                setState(oldState.copy(screenState = event.screenState))
            }
            is RootEvent.OnClickedBottomNaviItem -> {
                when (event.route) {
                    HomeRoutes.HOME -> {
                        setState(oldState.copy(screenState = ScreenState.HOME_SCREEN))
                    }

                    TimerRoute.ROUTE -> {
                        setState(oldState.copy(screenState = ScreenState.TIMER_SCREEN))
                    }

                    LaboratoryRoutes.LABORATORY -> {
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