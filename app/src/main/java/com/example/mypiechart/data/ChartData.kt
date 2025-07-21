package com.example.mypiechart.data

/**
 * 파이차트 렌더링을 위해 미리 계산된 데이터들을 담는 데이터 클래스
 *
 * @property totalValue 모든 파이 항목 값들의 총합
 * @property percentages 각 파이 항목의 백분율 리스트 (0.0~100.0)
 * @property formattedPercentages 화면 표시용으로 포맷된 백분율 문자열 리스트
 * @property angles 각 파이 조각의 중간 지점 각도 리스트
 */
data class ChartData(
    val totalValue: Float,
    val percentages: List<Float>,
    val formattedPercentages: List<String>,
    val angles: List<Float>
)