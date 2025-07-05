package com.kkh.multimodule.reservation

import android.R
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kkh.accessibility.AppInfo
import com.kkh.multimodule.designsystem.LimberColorStyle
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray400
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray500
import com.kkh.multimodule.designsystem.LimberTextStyle
import com.kkh.multimodule.ui.component.CheckAppList
import com.kkh.multimodule.ui.component.LimberCloseButton
import com.kkh.multimodule.ui.component.LimberOutlinedTextField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onClickComplete: (List<AppInfo>) -> Unit // ✅ 변경: 선택된 리스트를 넘겨줌
) {

    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        modifier = modifier,
        containerColor = Color.White,
        onDismissRequest = {
        },
        sheetState = sheetState
    ) {
        ReservationBottomSheetContent(onClickClose = {})
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ReservationBottomSheetPreview() {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        sheetState.show()
    }

    ReservationBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
            }
        },
        onClickComplete = {}
    )
}


@Preview(showBackground = true)
@Composable
fun ReservationBottomSheetContent(onClickClose: () -> Unit={}) {
    Column(Modifier.fillMaxSize()) {
        ReservationTopBar(onClickClose = onClickClose)
        Spacer(Modifier.height(33.dp))
        Column(Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
            Text("예약할 실험의 제목을 설정해주세요.", style = LimberTextStyle.Heading4)

            Spacer(Modifier.height(12.dp))
            LimberOutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = {
                    Text(
                        "ex. 포트폴리오 작업, 영어 공부",
                        style = LimberTextStyle.Body1,
                        color = Gray400
                    )
                }
            )
        }

    }

}

@Composable
fun ReservationTopBar(onClickClose: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, end = 20.dp)
    ) {
        Text(
            "실험 예약하기",
            style = LimberTextStyle.Heading4,
            modifier = Modifier.align(Alignment.Center)
        )
        LimberCloseButton(
            modifier = Modifier.align(Alignment.TopEnd),
            onClick = onClickClose
        )
    }
}