package com.kkh.multimodule.ui.component

import android.R.attr.onClick
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kkh.accessibility.AppInfo
import com.kkh.multimodule.designsystem.LimberColorStyle
import com.kkh.multimodule.designsystem.LimberTextStyle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterBlockAppBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    onDismissRequest: () -> Unit
) {

    var appList by remember {
        mutableStateOf(
            listOf(
                AppInfo("카카오톡", "com.kakao.talk", null, "6시간"),
                AppInfo("인스타그램", "com.instagram.android", null, "3시간"),
                AppInfo("유튜브", "com.google.android.youtube", null, "5시간"),
                AppInfo("슬랙", "com.slack", null, "1시간")
            )
        )
    }

    var checkedList by remember {
        mutableStateOf(List(appList.size) { false })
    }

    val scope = rememberCoroutineScope()

    ModalBottomSheet(
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
                        sheetState.hide() // ↓ 애니메이션 먼저 수행
                        onDismissRequest()
                    }
                }, modifier = Modifier.size(24.dp)) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Text(
                    "선택 완료",
                    modifier = Modifier.clickable(onClick = {}),
                    style = LimberTextStyle.Body2,
                    color = LimberColorStyle.Gray500
                )

            }
            Spacer(Modifier.height(20.dp))
            Text("관리할 앱을 최대 10개까지 등록해주세요", style = LimberTextStyle.Heading3)
            Spacer(Modifier.height(11.dp))
            Text(
                "${checkedList.count { it }}/${appList.size}",
                style = LimberTextStyle.Heading3
            )
            Spacer(Modifier.height(24.dp))

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
    onCheckClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(vertical = 13.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LimberCheckButton(isChecked = isChecked, onClick = onCheckClick)
            Text(appInfo.appName, style = LimberTextStyle.Heading3)
            Text(appInfo.usageTime, style = LimberTextStyle.Heading3)
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = LimberColorStyle.Gray400
        )
    }
}

@Composable
fun CheckAppList(
    appList: List<AppInfo>,
    checkedList: List<Boolean>,
    onCheckClicked: (Int) -> Unit
) {
    LazyColumn {
        itemsIndexed(appList) { index, item ->
            CheckAppItem(
                appInfo = item,
                isChecked = checkedList[index],
                onCheckClick = { onCheckClicked(index) }
            )
        }
    }
}



