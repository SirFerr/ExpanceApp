package com.example.expanceapp.data.remote

import java.time.YearMonth

data class MonthExpanse(
    val monthName: String = "",
    val monthSum: Double = -1.0,
    val expanses: List<Expanse> = listOf(),
)
