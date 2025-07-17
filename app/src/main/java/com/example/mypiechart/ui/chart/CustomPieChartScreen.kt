package com.example.mypiechart.ui.chart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mypiechart.data.PieEntry

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
    ) {
        Text(
            text = "내가 먹고 싶은 음식",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        MyCustomComposePieChart(
            data = sampleData,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
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
            drawArc(
                color = entry.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
            )
            startAngle += sweepAngle
        }

        drawCircle(color = Color.White, radius = holeRadius, center = center)
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    CustomPieChartScreen()
}