package com.example.mypiechart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.mypiechart.ui.chart.CustomPieChartScreen
import com.example.mypiechart.ui.chart.MPPieChartScreen
import com.example.mypiechart.ui.theme.MyPieChartTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyPieChartTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MPPieChartScreen(modifier = Modifier.padding(innerPadding))
//                    CustomPieChartScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}