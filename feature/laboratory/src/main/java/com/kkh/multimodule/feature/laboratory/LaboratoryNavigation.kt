package com.kkh.multimodule.feature.laboratory

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kkh.multimodule.core.domain.model.history.LatestTimerHistoryModel

object LaboratoryRoutes {
    const val LABORATORY = "laboratory"
}
fun NavController.navigateToLaboratoryScreen() =
    navigate(LaboratoryRoutes.LABORATORY)

fun NavGraphBuilder.laboratoryGraph(
    onNavigateToRecall : (LatestTimerHistoryModel) -> Unit
) {
    composable(LaboratoryRoutes.LABORATORY) {
        LaboratoryScreen(onNavigateToRecall = onNavigateToRecall)
    }
}
