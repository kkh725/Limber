package com.kkh.multimodule.feature.block

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlockActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val blockViewModel: BlockViewModel = hiltViewModel()
            val navHostController = rememberNavController()

            BlockNavHost(
                navController = navHostController,
                modifier = Modifier,
                onClickCloseScreen = { this.finishAffinity() },
                onNavigateToStartTimerNow = {
                    // 차단 실행
                    val uri = "limber://app".toUri()
                    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    this.startActivity(intent)

                    // 네비게이션 백스택 비우기
                    navHostController.popBackStack(
                        route = navHostController.graph.startDestinationRoute ?: return@BlockNavHost,
                        inclusive = true
                    )
                })
        }
    }

    override fun onResume() {
        super.onResume()
    }
}
