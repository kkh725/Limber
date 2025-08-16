package com.kkh.multimodule.feature.laboratory

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

object LaboratoryRoutes {
    const val LABORATORY = "laboratory"
}
fun NavController.navigateToLaboratoryScreen() =
    navigate(LaboratoryRoutes.LABORATORY)

fun NavGraphBuilder.laboratoryGraph(
) {
    composable(LaboratoryRoutes.LABORATORY) {
        LaboratoryScreen()
    }
}
