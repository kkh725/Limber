package com.kkh.multimodule.moduletest.intent

import com.kkh.multimodule.domain.model.ScreenState
import com.kkh.multimodule.moduletest.navigation.BottomNavRoutes

data class RootState(
    val screenState: ScreenState
) : UiState {
    companion object {
        fun init() = RootState(
            screenState = ScreenState.MAIN_SCREEN
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
                    BottomNavRoutes.HOME -> {
                        setState(oldState.copy(screenState = ScreenState.MAIN_SCREEN))
                    }

                    BottomNavRoutes.SEARCH -> {
                        setState(oldState.copy(screenState = ScreenState.MAIN_SCREEN))
                    }

                    BottomNavRoutes.NOTIFICATIONS -> {
                        setState(oldState.copy(screenState = ScreenState.MAIN_SCREEN))
                    }

                    BottomNavRoutes.SETTINGS -> {
                        setState(oldState.copy(screenState = ScreenState.MAIN_SCREEN))
                    }
                }
            }
        }
    }
}