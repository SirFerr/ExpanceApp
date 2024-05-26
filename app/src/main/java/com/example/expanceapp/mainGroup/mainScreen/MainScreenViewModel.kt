package com.example.expanceapp.mainGroup.mainScreen

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expanceapp.data.remote.Expanse
import com.example.expanceapp.data.remote.ExpanseAppApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    fun generateColorFromType(type: String): Color {
        val hash = type.hashCode()
        val r = (hash shr 16 and 0xFF) / 255.0f
        val g = (hash shr 8 and 0xFF) / 255.0f
        val b = (hash and 0xFF) / 255.0f
        return Color(r, g, b)
    }
}