package com.example.expanceapp.mainGroup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.expanceapp.R
import com.example.expanceapp.data.remote.Expanse
import com.example.expanceapp.data.remote.MonthExpanse
import kotlin.math.abs
import kotlin.random.Random


@Composable
fun ExpanseItem(
    expanse: Expanse,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.main_padding) * 2),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
        ) {
            Box(
                modifier = Modifier
                    .size(14.dp)
                    .background(
                        color = generateColorFromType(expanse.type),
                        shape = CircleShape
                    )
            )
            Text(text = expanse.name)
        }
        Text(text = "${expanse.value} рублей")
    }
}

@Composable
fun MonthExpanseHeader(monthExpanse: MonthExpanse) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = dimensionResource(id = R.dimen.main_padding)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = monthExpanse.monthName,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.main_padding)))
        HorizontalDivider(
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.main_padding)))
        Text(
            text = monthExpanse.monthSum.toString() + " Рублей",
            style = MaterialTheme.typography.titleMedium
        )
    }
}


fun generateColorFromType(type: String): Color {
    val hash = abs(type.hashCode())
    val random = Random(hash.toLong())

    // Генерируем значения оттенка, насыщенности и яркости
    val hue = (random.nextInt(360) / 360.0f)
    val saturation = 0.3f + (random.nextInt(40) / 100.0f) // от 0.3 до 0.7
    val lightness = 0.4f + (random.nextInt(20) / 100.0f) // от 0.4 до 0.6

    // Преобразование HSL в RGB
    val c = (1 - abs(2 * lightness - 1)) * saturation
    val x = c * (1 - abs((hue * 6) % 2 - 1))
    val m = lightness - c / 2

    val (r, g, b) = when {
        hue < 1 / 6.0 -> Triple(c, x, 0f)
        hue < 2 / 6.0 -> Triple(x, c, 0f)
        hue < 3 / 6.0 -> Triple(0f, c, x)
        hue < 4 / 6.0 -> Triple(0f, x, c)
        hue < 5 / 6.0 -> Triple(x, 0f, c)
        else -> Triple(c, 0f, x)
    }

    return Color(
        red = r + m,
        green = g + m,
        blue = b + m
    )
}

