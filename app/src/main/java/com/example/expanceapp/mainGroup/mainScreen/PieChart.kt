package com.example.expanceapp.mainGroup.mainScreen

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import com.example.expanceapp.data.remote.Expanse
import com.example.expanceapp.utils.ExpanseType

data class PieChartData(
    val value: Float,
    val color: Color
)

@Composable
fun PieChart(
    data: List<PieChartData>,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val totalValue = data.sumOf { it.value.toDouble() }.toFloat()
        var startAngle = 0f

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
    }
}

fun mapExpanseToPieChartData(expanses: List<Expanse>): List<PieChartData> {
    return expanses.map { expanse ->
        val expanseType = ExpanseType.fromDisplayName(expanse.type)
        PieChartData(
            value = expanse.value.toFloat(),
            color = expanseType?.color
                ?: Color.Black
        )
    }
}