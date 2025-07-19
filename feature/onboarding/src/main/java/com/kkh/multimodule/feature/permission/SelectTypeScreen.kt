package com.kkh.multimodule.feature.permission

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray600
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle.Gray800
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.core.ui.ui.component.LimberChip
import com.kkh.multimodule.core.ui.ui.component.LimberGradientButton
import com.kkh.multimodule.feature.onboarding.OnboardingViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectTypeScreen(navigateToStart: () -> Unit, onClickBack: () -> Unit) {

    val chipTexts = listOf("학습", "업무", "회의", "작업", "독서", "+")
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    var isSheetVisible by remember { mutableStateOf(false) }
    val viewModel: OnboardingViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SelectTopBar(
            Modifier.padding(top = 20.dp, bottom = 16.dp),
            onClickBack = onClickBack,
            onClickSkip = navigateToStart
        )
        LimberProgressBar(1f)
        Spacer(Modifier.height(40.dp))
        Text(
            "평소 스마트폰의 방해 없이\n" +
                    "집중하고 싶은 순간을 골라주세요",
            textAlign = TextAlign.Center,
            style = LimberTextStyle.Heading3,
            color = Gray800
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "선택한 목표는 타이머에 반영되며 언제든 변경 가능해요", style = LimberTextStyle.Body2, color = Gray600
        )
        Spacer(Modifier.height(40.dp))

        ChipGridScreen(
            chipTexts = chipTexts,
            selectedIndex = selectedIndex,
            onSelect = { newIndex ->
                selectedIndex = newIndex
            }
        )

        Spacer(Modifier.weight(1f))
        Box(
            Modifier
                .fillMaxWidth()
        ) {
            LimberGradientButton(
                enabled = selectedIndex != null,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    isSheetVisible = true
                    navigateToStart()
                },
                text = "다음으로"
            )
        }
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
fun SelectTopBar(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit = {},
    onClickSkip: () -> Unit = {}
) {
    Row(
        modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onClickBack, modifier = Modifier.size(24.dp)) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "ic_back"
            )
        }
        TextButton(onClickSkip, contentPadding = PaddingValues(0.dp)) {
            Text(
                "건너뛰기", style = LimberTextStyle.Body2, color = LimberColorStyle.Gray500
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChipGridScreen(
    chipTexts: List<String>,
    selectedIndex: Int?,
    onSelect: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(horizontal = 50.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(chipTexts) { index, text ->
            LimberChip(
                modifier = Modifier.wrapContentWidth(),
                text = text,
                isSelected = selectedIndex == index,
                onClick = {
                    onSelect(index)
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SelectTypeScreenPreview() {
}