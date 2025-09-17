package com.kkh.multimodule.feature.block

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.kkh.multimodule.core.accessibility.notification.NotificationManager
import com.kkh.multimodule.core.domain.repository.BlockReservationRepository
import com.kkh.multimodule.core.domain.repository.HistoryRepository
import com.kkh.multimodule.core.domain.repository.TimerRepository
import com.kkh.multimodule.feature.block.BlockViewModel
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BlockActivity : ComponentActivity() {

    @Inject lateinit var historyRepository: HistoryRepository
    @Inject lateinit var timerRepository: TimerRepository

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navHostController = rememberNavController()

            BlockNavHost(
                navController = navHostController,
                modifier = Modifier,
                onClickCloseScreen = { this.finishAffinity() },
                onNavigateToRecall = {
                    var uri = "limber://app".toUri()

                    // resValue로 생성된 문자열 리소스 참조
                    val buildType = getString(R.string.build_type)
                    val isProductBuild = buildType == "product"

                    if (isProductBuild){
                        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                        this@BlockActivity.startActivity(intent)
                    }else{
                        coroutineScope.launch {
                            val currentTimerId = timerRepository.getActiveTimerId()
                            historyRepository.getLatestHistoryId(currentTimerId)
                                .onSuccess {
                                    uri =
                                        "limber://recall?timerId=${currentTimerId}&timerHistoryId=${it}".toUri()
                                    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    }
                                    this@BlockActivity.startActivity(intent)
                                }.onFailure {
                                    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    }
                                    this@BlockActivity.startActivity(intent)
                                }
                        }
                    }

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
