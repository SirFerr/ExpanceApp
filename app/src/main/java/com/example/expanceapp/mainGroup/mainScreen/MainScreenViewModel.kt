package com.example.expanceapp.mainGroup.mainScreen

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expanceapp.data.remote.Expanse
import com.example.expanceapp.data.remote.ExpanseAppApi
import com.example.expanceapp.data.remote.MonthExpanse
import com.example.expanceapp.mainGroup.generateColorFromType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.Month
import javax.inject.Inject
import kotlin.math.abs
import kotlin.random.Random

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val expanseAppApi: ExpanseAppApi,
) : ViewModel() {

    val isAdd = MutableStateFlow(false)
    val isOpenedTypes = MutableStateFlow(false)
    val pieChartData = MutableStateFlow<List<PieChartData>>(emptyList())
    val expanseType = MutableStateFlow<List<String>>(listOf())

    init {
        fetchExpansesAmountByType()
        fetchExpansesType()
    }

    fun changeIsAdd() {
        isAdd.value = !isAdd.value
    }

    fun changeIsOpenedTypes() {
        isOpenedTypes.value = !isOpenedTypes.value
    }

    private fun fetchExpansesType() {
        viewModelScope.launch {
            val response = expanseAppApi.getAllTypes()
            if (response.isSuccessful) {
                expanseType.value = response.body()!!
            }
        }
    }


    fun createExpanse(expanse: Expanse) {
        viewModelScope.launch {
            try {
                val response = expanseAppApi.createExpanse(expanse)
                if (response.isSuccessful) {
                    fetchExpansesAmountByType()
                }
            } catch (e: Exception) {

            }

        }
    }

    fun generateRandomMonthExpanse(): MonthExpanse {

        val month = Month.entries.toTypedArray().random().name
        val numberOfExpanses = Random.nextInt(5, 15)
        val expanses = List(numberOfExpanses) {
            val id = it
            val name = "Expanse $id"
            val description = "Description for $name"
            val type = listOf("111", "222", "333", "444", "555", "666").random()
            val value = Random.nextInt(100, 1000)
            Expanse(
                id = id,
                name = name,
                description = description,
                type = type,
                value = value
            )
        }
        val monthSum = expanses.sumOf { it.value }
        return MonthExpanse(
            monthName = month,
            monthSum = monthSum.toDouble(),
            expanses = expanses
        )
    }

    fun fetchExpansesAmountByType() {
        viewModelScope.launch {
            val response = expanseAppApi.getExpansesAmountByType()
            if (response.isSuccessful) {
                val data = response.body().orEmpty()
                pieChartData.value = data.map { (type, amount) ->
                    PieChartData(
                        value = amount.toFloat(),
                        color = generateColorFromType(type),
                        name = type
                    )
                }
            }
        }
    }
}