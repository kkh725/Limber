package com.kkh.multimodule.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import com.kkh.accessibility.AppInfo
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray100
import com.kkh.multimodule.ui.component.DopamineActBox
import com.kkh.multimodule.ui.component.LimberHomeTopAppBar

@Composable
fun HomeScreen(
    onClickButtonTonNavigate: () -> Unit
) {

    val homeViewModel: HomeViewModel = hiltViewModel()
    val uiState by homeViewModel.uiState.collectAsState()
    val context = LocalContext.current

    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp

    val appInfoList = uiState.usageAppInfoList

    LaunchedEffect(Unit) {
        homeViewModel.sendEvent(HomeEvent.EnterHomeScreen(context))
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            LimberHomeTopAppBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                onNavigationClick = onClickButtonTonNavigate,
                onActionClick = {},
            )
        }
    ) { paddingValues ->

        Box(
            Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFB05AF6), Color(0xFF7329AF)),
                        start = Offset(0f, 0f), // 시작 지점
                        end = Offset(0f, (screenHeightDp).toFloat()) // 끝 지점 (예: 대각선)
                    )
                )
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HomeTopBackground()
            }
            // 메인 시트
            HomeMainSheet(modifier = Modifier.align(Alignment.BottomCenter), appInfoList)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = onClickButtonTonNavigate) {
                    Text("실험 시작하기")
                }
                Spacer(Modifier.fillMaxHeight(0.7f)) // 👈 하단 기준으로 띄움
            }

        }


//        if (appInfoList.isNotEmpty()) {
//            LazyColumn(
//                modifier = Modifier.padding(paddingValues)
//            ) {
//                items(appInfoList) { appInfo ->
//                    DopamineAppItem(appInfo)
//                }
//            }
//        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen {}
}

@Composable
fun HomeMainSheet(modifier: Modifier = Modifier, appInfoList: List<AppInfo>) {
    Box(
        modifier = modifier
            .fillMaxHeight(0.7f)
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)) // 위쪽만 라운드
            .background(color = Gray100)

    ) {
        // Content here
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(bottom = 20.dp)
        ) {
            Spacer(Modifier.height(54.dp))
            Text("6월 20일 금요일")
            Spacer(Modifier.height(8.dp))
            HomeMainContent(appInfoList)
        }
    }
}

@Composable
fun HomeMainContent(appInfoList: List<AppInfo>) {
    Box(
        Modifier
//            .shadow(elevation = 20.dp)
            .fillMaxWidth()
            .height(327.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(horizontal = 24.dp)
            .padding(top = 24.dp, bottom = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // 콘텐츠 영역
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("집중한 시간")
                    Text("집중한 시간")
                }

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("집중한 시간")
                    Text("집중한 시간")
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        Modifier
                            .size(120.dp, 24.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Blue)
                    )

                    Spacer(Modifier.width(2.dp))

                    Box(
                        Modifier
                            .weight(1f)
                            .height(24.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Red)
                    )
                }
                Spacer(Modifier.height(24.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(106.dp)
                ) {
                    FocusActBox(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    VerticalDivider()

                    Spacer(modifier = Modifier.width(20.dp))

                    DopamineActBox(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        appInfoList = appInfoList
                    )
                }
            }

            // 🔽 버튼 - 아래 고정
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Button")
//                    Icon(
//                        modifier = Modifier.size(24.dp),
//                        painter = painterResource(R.drawable.ic_next),
//                        contentDescription = "Arrow Forward"
//                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeTopBackground() {
    Box(
        modifier = Modifier
            .height(188.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.bg_half_circle),
            modifier = Modifier
                .size(286.dp, 143.dp)
                .fillMaxSize()
                .align(Alignment.BottomCenter),
            contentDescription = "bg_half_circle"
        )
        Column(
            modifier = Modifier
                .size(120.dp)
                .fillMaxSize()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo_limber),
                contentDescription = "",

                )
            Spacer(Modifier.height(14.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .wrapContentSize()
                    .clickable(onClick = {})
            ) {
                Text("Button")
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.ic_next),
                    contentDescription = "Arrow Forward"
                )
            }
        }
    }
}

@Composable
fun FocusActBox(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .width(8.dp)
                    .height(8.dp)
                    .background(
                        color = Color(0xFFB05AF6),
                        shape = RoundedCornerShape(size = 100.dp)
                    )
            )
            Spacer(Modifier.width(6.dp))
            Text("집중한 시간")
        }
        Spacer(Modifier.height(16.5.dp))
        Row {
            Text("학습")
            Spacer(Modifier.width(10.dp))
            Text("1시간 2분")
        }
        Row {
            Text("학습")
            Spacer(Modifier.width(10.dp))
            Text("1시간 2분")
        }
        Row {
            Text("학습")
            Spacer(Modifier.width(10.dp))
            Text("1시간 2분")
        }
    }
}
