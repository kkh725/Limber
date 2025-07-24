package com.kkh.multimodule.core.ui.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.core.accessibility.AppInfo
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray100
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray400
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray500
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterBlockAppBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onClickComplete: (List<AppInfo>) -> Unit, // ✅ 변경: 선택된 리스트를 넘겨줌,
    appList: List<AppInfo>
) {

    var checkedList by remember {
        mutableStateOf(List(appList.size) { false })
    }

    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        modifier = modifier,
        containerColor = Color.White,
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
                onDismissRequest()
            }
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    scope.launch {
                        sheetState.hide()
                        onDismissRequest()
                    }
                }, modifier = Modifier.size(24.dp)) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize(),
                        tint = Gray400
                    )
                }
                TextButton(onClick = {
                    // ✅ 선택된 앱 리스트 추출
                    val selectedApps = appList.filterIndexed { index, _ -> checkedList[index] }

                    scope.launch {
                        sheetState.hide()
                        onClickComplete(selectedApps) // ✅ 선택된 리스트를 넘김
                    }
                }, contentPadding = PaddingValues(0.dp)) {
                    Text(
                        "선택 완료",
                        style = LimberTextStyle.Body2,
                        color = LimberColorStyle.Primary_Main
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            Text("관리할 앱을 최대 10개까지 등록해주세요", style = LimberTextStyle.Heading4, color = Gray500)
            Spacer(Modifier.height(11.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(100.dp))
                    .background(LimberColorStyle.Primary_BG_Dark)
                    .padding(vertical = 4.dp, horizontal = 12.dp)
            ) {
                Row {
                    Text(
                        "${checkedList.count { it }}",
                        style = LimberTextStyle.Heading4, color = LimberColorStyle.Primary_Main
                    )
                    Text(
                        "/${appList.size}",
                        style = LimberTextStyle.Heading4
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
            RegisterAppNotice()

            CheckAppList(
                appList = appList,
                checkedList = checkedList,
                onCheckClicked = { index ->
                    checkedList = checkedList.toMutableList().also {
                        it[index] = !it[index]
                    }
                }
            )
        }
    }
}


@Composable
fun CheckAppItem(
    appInfo: AppInfo,
    isChecked: Boolean,
    onCheckClick: (Boolean) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .clickable { onCheckClick(!isChecked) }
                .fillMaxWidth()
                .height(56.dp)
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LimberCheckButton(isChecked = isChecked, onClick = onCheckClick)
                Text(appInfo.appName, style = LimberTextStyle.Body2)
            }
            Text(appInfo.usageTime, style = LimberTextStyle.Body2, color = Gray600)
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = LimberColorStyle.Gray400
        )
    }
}

// 10개 이상인 경우는 체크 불가능
@Composable
fun CheckAppList(
    appList: List<AppInfo>,
    checkedList: List<Boolean>,
    onCheckClicked: (Int) -> Unit
) {
    if (checkedList.isEmpty()) {
        return
    }
    val checkedCount = checkedList.count { it }

    if (appList.isEmpty()) {
        Text(
            "앱이 없습니다.",
            style = LimberTextStyle.Body2,
            color = Gray600
        )
    } else {
        LazyColumn {
            itemsIndexed(appList) { index, item ->
                val canCheck = checkedCount < 10 || checkedList[index] // 이미 선택된 경우는 해제할 수 있어야 함

                CheckAppItem(
                    appInfo = item,
                    isChecked = checkedList[index],
                    onCheckClick = {
                        if (canCheck) {
                            onCheckClicked(index)
                        }
                    }
                )
            }
        }
    }

}


@Composable
fun RegisterAppNotice() {
    Row(
        Modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Gray100)
            .padding(start = 12.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_info),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            "최근 한 달 동안 하루 평균 사용 시간이 많은 순서예요",
            style = LimberTextStyle.Body2,
            color = Gray600
        )
    }
}



