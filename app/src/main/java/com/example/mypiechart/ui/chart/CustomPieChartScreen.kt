package com.example.mypiechart.ui.chart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mypiechart.data.PieEntry
import java.lang.Math.toRadians
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CustomPieChartScreen(
    modifier: Modifier = Modifier,
) {
    val sampleData = remember {
        listOf(
            PieEntry(30f, "국밥", Color(0xFFFF6B6B)),
            PieEntry(25f, "초밥", Color(0xFF4ECDC4)),
            PieEntry(20f, "떡볶이", Color(0xFF45B7D1)),
            PieEntry(15f, "치킨", Color(0xFF96CEB4)),
            PieEntry(10f, "연어", Color(0xFFFFEAA7))
        )
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "내가 먹고 싶은 음식",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        MyCustomComposePieChart(
            data = sampleData,
            size = 240.dp,
        )
    }
}

@Composable
fun MyCustomComposePieChart(
    data: List<PieEntry>,
    modifier: Modifier = Modifier,
    size: Dp = 240.dp,
    holeRatio: Float = 0.5f,
    labelDistanceRatio: Float = 1.2f,
) {
    val chartModifier = if (size != null) {
        modifier.size(size)
    } else {
        modifier
    }

    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = chartModifier) {
        // 차트 설정
        val totalValue = data.sumOf { it.value.toDouble() }.toFloat()
        var startAngle = 270f

        // 중앙 원
        val chartRadius = this.size.minDimension / 2f
        val holeRadius = chartRadius * holeRatio

        data.forEach { entry ->
            val sweepAngle = (entry.value / totalValue) * 360f
            val percentage = (entry.value / totalValue * 100)
            val formattedPercentage = "%.1f".format(percentage)
            val midAngle = (startAngle + sweepAngle / 2f) % 360f

            drawArc(
                color = entry.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
            )

            // 연결 선과 라벨 그리기
            drawLabelWithLine(
                text = "${entry.label}\n${formattedPercentage}%",
                angle = midAngle,
                innerRadius = chartRadius, // 선 시작점 (파이 차트의 반지름)
                outerRadius = chartRadius * labelDistanceRatio, // 선이 끝나는 점
                color = entry.color,
                textMeasurer = textMeasurer
            )

            startAngle += sweepAngle
        }

        drawCircle(color = Color.White, radius = holeRadius, center = center)
    }
}

/**
 * 지정된 각도와 위치에 라벨과 연결선을 그림
 *
 * @param text 표시할 텍스트 (라벨명과 백분율 포함)
 * @param angle 연결선이 향할 각도
 * @param innerRadius 연결선 시작점까지의 거리
 * @param outerRadius 연결선 끝점까지의 거리
 * @param color 연결선 색상
 * @param textMeasurer 텍스트 크기 측정 객체
 */
private fun DrawScope.drawLabelWithLine(
    text: String,
    angle: Float,
    innerRadius: Float,
    outerRadius: Float,
    color: Color,
    textMeasurer: TextMeasurer,
) {
    val angleInRadians = toRadians(angle.toDouble()).toFloat()

    // 선의 시작점과 끝점 계산
    val lineStart = Offset(
        center.x + innerRadius * cos(angleInRadians),
        center.y + innerRadius * sin(angleInRadians)
    )
    val lineEnd = Offset(
        center.x + outerRadius * cos(angleInRadians),
        center.y + outerRadius * sin(angleInRadians)
    )

    // 선 그리기
    drawLine(
        color = color,
        start = lineStart,
        end = lineEnd,
        strokeWidth = 2.dp.toPx()
    )

    // 텍스트 그리기
    drawTextLabel(
        text = text,
        angle = angle,
        lineEnd = lineEnd,
        textMeasurer = textMeasurer
    )
}

/**
 * 연결선 끝에 텍스트 라벨을 그림
 *
 * @param text 표시할 텍스트 (줄바꿈 문자로 여러 줄 지원)
 * @param angle 텍스트 위치를 결정하는 각도 (정렬 방향 계산용)
 * @param lineEnd 연결선의 끝점 좌표 (텍스트 기준점)
 * @param textMeasurer 텍스트 크기 측정 객체
 */
private fun DrawScope.drawTextLabel(
    text: String,
    angle: Float,
    lineEnd: Offset,
    textMeasurer: TextMeasurer
) {
    val textStyle = TextStyle(
        fontSize = 14.sp,
        color = Color.Black,
        fontWeight = FontWeight.Normal
    )

    val lines = text.split("\n")
    val textPadding = 8.dp.toPx()

    lines.forEachIndexed { index, line ->
        val textLayoutResult = textMeasurer.measure(line, textStyle)
        val textWidth = textLayoutResult.size.width
        val textHeight = textLayoutResult.size.height

        // 텍스트 위치 계산 (각도에 따라 좌우 정렬)
        val textX = if (angle > 90f && angle < 270f) {
            lineEnd.x - textPadding - textWidth // 왼쪽 반원: 오른쪽 정렬
        } else {
            lineEnd.x + textPadding // 오른쪽 반원: 왼쪽 정렬
        }

        // 여러 줄 텍스트의 세로 중앙 정렬
        val textY = lineEnd.y + (index - lines.size / 2f + 0.5f) * textHeight

        drawText(
            textLayoutResult = textLayoutResult,
            topLeft = Offset(textX, textY - textHeight / 2)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    CustomPieChartScreen()
}