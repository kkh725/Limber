package com.kkh.multimodule.feature.laboratory.report

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.core.domain.model.RecallItemModel
import com.kkh.multimodule.core.domain.model.mockRecallItems
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray200
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray500
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.core.ui.ui.component.LimberChip
import com.kkh.multimodule.core.ui.ui.component.LimberColumnChart
import com.kkh.multimodule.core.ui.ui.component.LimberFilterChip
import com.kkh.multimodule.core.ui.ui.component.LimberRoundButton
import com.kkh.multimodule.core.ui.ui.component.LimberText
import com.kkh.multimodule.core.ui.ui.component.TextSwitch

@Preview(showBackground = true)
@Composable
fun RecallPagerContent() {
    // 0: 전체, 1: 주간
    var selectedFilter by remember { mutableIntStateOf(0) }
    var radioSelected by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(LimberColorStyle.Gray50)
            .padding(vertical = 24.dp, horizontal = 20.dp)
    ) {
        RecallTopBar(
            selectedFilter = selectedFilter,
            onFilterClick = { selectedFilter = it },
            radioSelected = radioSelected,
            onRadioClick = { radioSelected = !radioSelected }
        )
        Spacer(Modifier.height(23.dp))
        RecallContent()
    }
}

@Composable
fun RecallTopBar(
    selectedFilter: Int, // 0: 전체, 1: 주간
    onFilterClick: (Int) -> Unit,
    radioSelected: Boolean,
    onRadioClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            LimberFilterChip(
                text = "전체",
                checked = selectedFilter == 0,
                onCheckedChange = { if (!it) return@LimberFilterChip else onFilterClick(0) }
            )
            Spacer(Modifier.width(8.dp))
            LimberFilterChip(
                text = "주간",
                checked = selectedFilter == 1,
                onCheckedChange = { if (!it) return@LimberFilterChip else onFilterClick(1) }
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                modifier = Modifier.size(24.dp),
                selected = radioSelected,
                onClick = onRadioClick
            )
            Spacer(Modifier.width(8.dp))
            LimberText(
                "미회고만 보기",
                style = LimberTextStyle.Body2,
                color = Gray600
            )
        }
    }
}

@Composable
fun RecallContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        RecallItemList()
        Spacer(modifier = Modifier.height(40.dp))
        RecallItemList()
    }
}

@Composable
fun RecallItemList(
    period : String = "2025년 5월 24일~31일",
    recallItems: List<RecallItemModel> = mockRecallItems(),
    onRecallClick: (RecallItemModel) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LimberText(
            text = period,
            style = LimberTextStyle.Heading5,
            color = Gray800,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        recallItems.forEachIndexed { index, item ->
            RecallCard(item, onRecallClick)
            if (index < recallItems.size - 1){
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun RecallCard(
    item: RecallItemModel,
    onRecallClick: (RecallItemModel) -> Unit
) {
    Row(
        modifier = Modifier
            .shadow(
                elevation = 12.dp,
                spotColor = Color(0x14000000),
                ambientColor = Color(0x14000000)
            )
            .fillMaxWidth()
            .height(116.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp))
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row{
                LimberText(
                    item.date,
                    style = LimberTextStyle.Body2,
                    color = LimberColorStyle.Gray500
                )
                Spacer(Modifier.width(4.dp))
                LimberText(
                    item.focusTime,
                    style = LimberTextStyle.Body2,
                    color = LimberColorStyle.Gray500
                )
            }

            Spacer(Modifier.height(4.dp))
            Row {
                LimberText(
                    item.type,
                    style = LimberTextStyle.Heading5,
                    color = LimberColorStyle.Primary_Main
                )
                LimberText(
                    "에 집중한 시간",
                    style = LimberTextStyle.Heading5,
                    color = Gray800
                )
            }

            if (item.subTitle.isNotBlank()) {
                Spacer(Modifier.height(10.dp))
                LimberText(
                    item.subTitle,
                    style = LimberTextStyle.Body2,
                    color = LimberColorStyle.Gray600
                )
            }
            Spacer(Modifier.height(8.dp))

        }
        if (item.isRecalled){
            Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.ic_study),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp) // or 32.dp, 40.dp 등
                )
            }
        }else{
            Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomCenter) {
                LimberRoundButton(
                    modifier = Modifier,
                    text = "회고하기",
                    enabled = true,
                    onClick = { onRecallClick(item) }
                )
            }
        }
    }
}
