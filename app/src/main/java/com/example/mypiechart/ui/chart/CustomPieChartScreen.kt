package com.example.mypiechart.ui.chart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
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
            modifier = Modifier
                .aspectRatio(1f)
                .padding(40.dp)
        )
    }
}

@Composable
fun MyCustomComposePieChart(
    data: List<PieEntry>,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        // 차트 설정
        val totalValue = data.sumOf { it.value.toDouble() }.toFloat()
        var startAngle = 270f

        // 중앙 원
        val chartRadius = size.minDimension / 2f
        val holeRadius = chartRadius * 0.5f

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
                outerRadius = chartRadius * 1.2f, // 선이 끝나는 점
                color = entry.color,
            )

            startAngle += sweepAngle
        }

        drawCircle(color = Color.White, radius = holeRadius, center = center)
    }
}

private fun DrawScope.drawLabelWithLine(
    text: String,
    angle: Float,
    innerRadius: Float,
    outerRadius: Float,
    color: Color,
) {
    val angleInRadians = toRadians(angle.toDouble()).toFloat()

    val textPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        textSize = 14.sp.toPx()
        textAlign = android.graphics.Paint.Align.LEFT
    }

    // 선의 시작점
    val lineStartX = center.x + innerRadius * cos(angleInRadians)
    val lineStartY = center.y + innerRadius * sin(angleInRadians)

    // 선의 끝점
    val lineEndX = center.x + outerRadius * cos(angleInRadians)
    val lineEndY = center.y + outerRadius * sin(angleInRadians)

    // 선 그리기
    drawLine(
        color = color,
        start = Offset(lineStartX, lineStartY),
        end = Offset(lineEndX, lineEndY),
        strokeWidth = 2.dp.toPx()
    )

    // 텍스트 위치 계산
    val lines = text.split("\n")
    val textPadding = 8.dp.toPx()
    val lineHeight = textPaint.fontSpacing

    val textAnchorX: Float
    val textAnchorY: Float = lineEndY - (lines.size - 1) * lineHeight / 2

    if (angle > 90f && angle < 270f) {
        textAnchorX = lineEndX - textPadding
        textPaint.textAlign = android.graphics.Paint.Align.RIGHT
    } else {
        textAnchorX = lineEndX + textPadding
        textPaint.textAlign = android.graphics.Paint.Align.LEFT
    }


    lines.forEachIndexed { index, line ->
        drawContext.canvas.nativeCanvas.drawText(
            line,
            textAnchorX,
            textAnchorY + index * lineHeight,
            textPaint
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    CustomPieChartScreen()
}