package com.kkh.multimodule.core.ui.ui.component

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kkh.multimodule.core.ui.designsystem.LimberColorStyle
import com.kkh.multimodule.core.ui.designsystem.LimberTextStyle
import com.kkh.multimodule.core.ui.util.getTodayDayOfWeek
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.Bars.Data.Radius.Circular
import ir.ehsannarmani.compose_charts.models.Bars.Data.Radius.Rectangle
import ir.ehsannarmani.compose_charts.models.DividerProperties
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LineProperties
import ir.ehsannarmani.compose_charts.models.VerticalIndicatorProperties
import kotlin.Float
import kotlin.Pair
import kotlin.random.Random

@Composable
fun RoundedCircularProgressBar(
    progress: Float, // 0.0 ~ 1.0
    modifier: Modifier = Modifier,
    size: Dp = 280.dp,
    strokeWidth: Dp = 12.dp,
    backgroundColor: Color = Color.LightGray,
    progressColor: Color = Color(0xFF3DDC84),
    animated: Boolean = true
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 800),
        label = "progressAnim"
    )

    Canvas(modifier = modifier.size(size)) {
        // Draw background ring
        drawArc(
            color = backgroundColor,
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(
                width = strokeWidth.toPx(),
                cap = StrokeCap.Round
            )
        )

        // Draw progress arc with rounded cap
        drawArc(
            color = progressColor,
            startAngle = -90f,
            sweepAngle = animatedProgress * 360f,
            useCenter = false,
            style = Stroke(
                width = strokeWidth.toPx(),
                cap = StrokeCap.Round
            )
        )
    }
}

@Preview
@Composable
fun CircularProgressPreview() {
    // 상태로 선언해야 변경 가능
    var progress by remember { mutableFloatStateOf(generateRandomProgress()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        RoundedCircularProgressBar(progress = progress)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            progress = generateRandomProgress()
        }) {
            Text("Progress 변경")
        }
    }
}

// ✅ 랜덤 퍼센트 생성 함수 (0.3f ~ 1.0f)
fun generateRandomProgress(): Float {
    return Random.nextFloat() * 0.7f + 0.3f
}

@Preview
@Composable
fun LimberColumnChart(
    modifier: Modifier = Modifier, chartData: List<Bars> = listOf(
        Bars(
            label = "월",
            values = listOf(
                Bars.Data(
                    value = 12.0,
                    color = SolidColor(LimberColorStyle.Primary_Light)
                )
            )
        ),
        Bars(
            label = "화",
            values = listOf(
                Bars.Data(
                    value = 20.0,
                    color = SolidColor(LimberColorStyle.Primary_Light)
                )
            )
        ),
        Bars(
            label = "수",
            values = listOf(
                Bars.Data(
                    value = 8.0,
                    color = SolidColor(LimberColorStyle.Primary_Light)
                )
            )
        ),
        Bars(
            label = "목",
            values = listOf(
                Bars.Data(
                    value = 17.0,
                    color = SolidColor(LimberColorStyle.Primary_Light)
                )
            )
        ),
        Bars(
            label = "금",
            values = listOf(
                Bars.Data(
                    value = 14.0,
                    color = SolidColor(LimberColorStyle.Primary_Light)
                )
            )
        ),
        Bars(
            label = "토",
            values = listOf(
                Bars.Data(
                    value = 22.0,
                    color = SolidColor(LimberColorStyle.Primary_Light)
                )
            )
        ),
        Bars(
            label = "일",
            values = listOf(
                Bars.Data(
                    value = 16.0,
                    color = SolidColor(LimberColorStyle.Primary_Light)
                )
            )
        )
    )
) {

    // ColumnChart 직접 적용
    ColumnChart(
        modifier = modifier
            .fillMaxWidth()
            .height(230.dp),
        data = chartData,
        barProperties = BarProperties(
            thickness = 28.dp,
            cornerRadius = Rectangle(
                topLeft = 10.dp,
                topRight = 10.dp,
                bottomLeft = 0.dp,
                bottomRight = 0.dp
            )
        ),
        labelProperties = ir.ehsannarmani.compose_charts.models.LabelProperties(
            enabled = true,
            textStyle = LimberTextStyle.graphBody3_Primary_Vivid,
            labels = chartData.map { it.label },
            builder = { modifier, label, shouldRotate, _ ->
                Text(
                    text = label,
                    style = if (label == getTodayDayOfWeek())
                        LimberTextStyle.graphBody3_Primary_Vivid
                    else
                        LimberTextStyle.graphBody3_Gray400,
                )
            }
        ),
        indicatorProperties = HorizontalIndicatorProperties(
            enabled = true,
            textStyle = LimberTextStyle.graphBody3_Gray400,
            contentBuilder = { value ->
                "${value.toInt()}h"
            }
        ),
        dividerProperties = DividerProperties(
            enabled = true,
            yAxisProperties = LineProperties(enabled = false)
        ),
        gridProperties = GridProperties(
            enabled = true, // 이 줄이 격자(가로 구분선) 비활성화 핵심입니다
            yAxisProperties = GridProperties.AxisProperties(enabled = false)
        )
        // 필요시 onBarClick, onBarLongClick, gridProperties 등 더 지정!
    )
}
