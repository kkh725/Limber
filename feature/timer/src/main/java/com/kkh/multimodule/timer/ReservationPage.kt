package com.kkh.multimodule.timer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.designsystem.LimberColorStyle
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray200
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray300
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray400
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.designsystem.LimberColorStyle.Primary_Main
import com.kkh.multimodule.designsystem.LimberTextStyle
import com.kkh.multimodule.ui.component.LimberFilterChip
import com.kkh.multimodule.ui.component.LimberToggle

data class ReservationInfo(
    val id: Int,
    val title: String,
    val description: String,
    val chipText: String,
    var isChecked: Boolean
)


@Composable
fun ReservationPage(modifier: Modifier = Modifier) {

    var reservationList by remember {
        mutableStateOf(
            listOf(
                ReservationInfo(1, "포트폴리오 작업하기", "매일 오전 8시에 업데이트", "작업", false),
                ReservationInfo(2, "운동 루틴", "매주 월, 수, 금 알림", "건강", true),
                ReservationInfo(3, "스터디 준비", "주말에 집중", "공부", false)
            )
        )
    }


    Column(modifier.fillMaxSize()) {
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .padding(horizontal = 20.dp)
        )
        ReservationTopBar(onClickModify = {})
        Spacer(Modifier.height(12.dp))

        ReservationList(
            reservationList = reservationList,
            onToggleChanged = { id, checked ->
                reservationList = reservationList.map {
                    if (it.id == id) it.copy(isChecked = checked) else it
                }
            }
        )

    }
}

@Preview(showBackground = true)
@Composable
fun ReservationListScreenPreview() {
    ReservationPage()
}

@Composable
fun ReservationTopBar(onClickModify: () -> Unit = {}) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("진행중인 실험", style = LimberTextStyle.Heading4, color = Gray800)
            Spacer(Modifier.width(4.dp))
            Text("3", style = LimberTextStyle.Heading4, color = LimberColorStyle.Primary_Main)
        }
        ModifyButton(onClick = onClickModify)
    }
}

@Composable
fun ModifyButton(onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .clickable(onClick = onClick)
            .background(Gray300)
            .padding(horizontal = 12.dp, vertical = 10.dp)
            ,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_write),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = "편집하기",
            style = LimberTextStyle.Body2,
            color = Gray600
        )
    }
}

@Composable
fun ReservationList(
    reservationList: List<ReservationInfo>,
    onToggleChanged: (id: Int, checked: Boolean) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(reservationList) { item ->
            ReservationItemComposable(
                info = item,
                onToggleChanged = { checked ->
                    onToggleChanged(item.id, checked)
                }
            )
        }
    }
}

@Composable
fun ReservationItemComposable(
    info: ReservationInfo,
    onToggleChanged: (Boolean) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .shadow(4.dp, RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(20.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LimberFilterChip(
                text = info.chipText,
                textColor = Primary_Main,
                backgroundColor = LimberColorStyle.Primary_Background_Dark,
                onclick = {}
            )
            LimberToggle(
                checked = info.isChecked,
                onCheckedChange = onToggleChanged,
                modifier = Modifier
            )
        }
        Spacer(Modifier.height(12.dp))
        Text(
            text = info.title,
            style = LimberTextStyle.Heading3,
            color = Gray800
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = info.description,
            style = LimberTextStyle.Body2,
            color = Gray600
        )
    }
}
