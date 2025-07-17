package com.example.mypiechart.ui.chart

import android.graphics.Color
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.toColorInt
import com.example.mypiechart.ui.theme.MyPieChartTheme
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet

@Composable
fun MPPieChartScreen(
    modifier: Modifier = Modifier,
) {
    val pieData = remember {
        listOf(
            com.github.mikephil.charting.data.PieEntry(30f, "국밥"),
            com.github.mikephil.charting.data.PieEntry(25f, "초밥"),
            com.github.mikephil.charting.data.PieEntry(20f, "떡볶이"),
            com.github.mikephil.charting.data.PieEntry(15f, "치킨"),
            com.github.mikephil.charting.data.PieEntry(10f, "연어")
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

        PieChartCompose(
            data = pieData,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        )
    }
}

@Composable
fun PieChartCompose(
    data: List<com.github.mikephil.charting.data.PieEntry>,
    modifier: Modifier = Modifier,
) {
    AndroidView(
        factory = { context ->
            PieChart(context).apply {
                // 기본 설정
                description.isEnabled = false
                isRotationEnabled = false
                isHighlightPerTapEnabled = true
                isDrawHoleEnabled = true
                holeRadius = 50f

                setDrawEntryLabels(true)
                setUsePercentValues(true)

                // 범례 설정
                legend.isEnabled = true
            }
        },
        modifier = modifier,
        update = { chart ->
            // 데이터 설정
            val dataSet = PieDataSet(data, "").apply {
                colors = listOf(
                    "#FF6B6B".toColorInt(),
                    "#4ECDC4".toColorInt(),
                    "#45B7D1".toColorInt(),
                    "#96CEB4".toColorInt(),
                    "#FFEAA7".toColorInt()
                )
                valueTextSize = 12f
                valueTextColor = Color.BLACK
            }

            chart.data = PieData(dataSet)
            chart.invalidate()
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MyScreenPreview() {
    MyPieChartTheme {
        MPPieChartScreen()
    }
}