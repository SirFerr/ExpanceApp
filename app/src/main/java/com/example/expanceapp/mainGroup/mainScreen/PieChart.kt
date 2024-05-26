package com.example.expanceapp.mainGroup.mainScreen

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import com.example.expanceapp.data.remote.Expanse

data class PieChartData(
    val value: Float,
    val color: Color,
    val name: String
)

@Composable
fun PieChart(
    data: List<PieChartData>,
    modifier: Modifier = Modifier,
    holeRadiusFraction: Float = 0.60f, // Размер отверстия по отношению к диаграмме
    backgroundColor: Color = Color.White // Цвет фона для отверстия
) {
    Canvas(modifier = modifier) {
        val totalValue = data.sumOf { it.value.toDouble() }.toFloat()
        var startAngle = 0f

        val outerRadius = size.minDimension / 2
        val holeRadius = outerRadius * holeRadiusFraction
        val center = Offset(size.width / 2, size.height / 2)

        data.forEach { pieChartData ->
            val sweepAngle = (pieChartData.value / totalValue) * 360f
            drawArc(
                color = pieChartData.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = Offset(0f, 0f),
                size = Size(size.width, size.height)
            )
            startAngle += sweepAngle
        }

        // Рисуем отверстие по середине с цветом фона
        drawCircle(
            color = backgroundColor,
            radius = holeRadius,
            center = center
        )
    }
}