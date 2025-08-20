package com.kkh.more

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.core.ui.R
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.core.ui.designsystem.gradientModifier
import com.kkh.multimodule.core.ui.ui.component.LimberChip
import com.kkh.multimodule.core.ui.ui.component.LimberGradientButton

@Composable
fun MoreScreen() {

}

@Preview(showBackground = true)
@Composable
fun MoreContents() {
    Column(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth().background(Color(0xFFF7ECFF)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(32.dp))
            Image(
                painter = painterResource(R.drawable.ic_lv1_good),
                contentDescription = "Level 1",
                modifier = Modifier.size(140.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100.dp))
                        .then(gradientModifier())
                        .padding(
                            horizontal = 10.dp, vertical = 4.dp
                        ), contentAlignment = Alignment.Center
                ) {
                    Text("LV.1", style = LimberTextStyle.Caption1, color = Color.White)

                }
                Spacer(Modifier.width(8.dp))
                Text("집중 입문자", style = LimberTextStyle.Heading3, color = LimberColorStyle.Gray800)
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.ic_next),
                        contentDescription = "",
                        tint = LimberColorStyle.Gray400
                    )
                }
            }
            Spacer(Modifier.height(32.dp))
            MoreItemList()
            Spacer(Modifier.height(20.dp))

        }

        MenuList()
    }
}

@Composable
fun MoreItemList() {

    val moreList = listOf(
        Pair(R.drawable.ic_focus, "집중 상황"),
        Pair(R.drawable.ic_interrupt, "방해하는 앱"),
        Pair(R.drawable.ic_coach, "AI 집중 코칭"),
    )

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(84.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(moreList.size) {
            MoreItem(
                modifier = Modifier.weight(1f),
                imageRes = moreList[it].first,
                text = moreList[it].second
            )
        }
    }
}

@Composable
fun MoreItem(
    modifier: Modifier = Modifier,
    imageRes: Int,
    text: String
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White, shape = RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(imageRes), contentDescription = "")
            Spacer(Modifier.height(8.dp))
            Text(text, style = LimberTextStyle.Body2, color = LimberColorStyle.Gray800)
        }
    }
}

@Composable
fun MenuList() {
    Column {
        MenuItem("림버의 스토리")
        MenuItem("이용 약관")
        MenuItem("개인 정보 처리 방침")

    }
}

@Composable
fun MenuItem(text: String) {
    Column(Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)) {
        Row(
            Modifier
                .fillMaxWidth().padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text, style = LimberTextStyle.Body2, color = LimberColorStyle.Gray800)
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.ic_next),
                    contentDescription = "",
                    tint = LimberColorStyle.Gray400
                )
            }
        }
        HorizontalDivider()
    }
}

