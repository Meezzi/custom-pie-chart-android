package com.example.mypiechart.data

import androidx.compose.ui.unit.Dp

/**
 * 차트를 그리는 데 필요한 설정 값들을 담는 데이터 클래스
 *
 * @property chartRadiusPx 차트 본체의 반지름
 * @property totalCanvasSize 라벨과 선을 포함한 전체 캔버스 크기
 * @property maxTextWidth 가장 긴 라벨 텍스트의 너비
 * @property textPadding 연결선과 텍스트 사이의 간격
 * @property lineExtension 차트 가장자리에서 라벨로 뻗어나가는 선의 길이
 */
data class ChartConfig(
    val chartRadiusPx: Float,
    val totalCanvasSize: Dp,
    val maxTextWidth: Dp,
    val textPadding: Dp,
    val lineExtension: Dp
)