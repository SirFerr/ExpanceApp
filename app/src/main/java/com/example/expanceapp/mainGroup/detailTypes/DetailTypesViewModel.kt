package com.example.expanceapp.mainGroup.detailTypes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expanceapp.data.remote.Auth
import com.example.expanceapp.data.remote.Expanse
import com.example.expanceapp.data.remote.ExpanseAppApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailTypesViewModel @Inject constructor(private val expanseAppApi: ExpanseAppApi) :
    ViewModel() {
    val expanses = MutableStateFlow<List<Expanse>>(listOf())
    var isLoading = MutableStateFlow(false)
    val isSuccessful = MutableStateFlow(true)
    val isFound = MutableStateFlow(false)


    fun getExpansesByType(string: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = expanseAppApi.getExpansesByType(string)
                isSuccessful.value = response.isSuccessful
                if (response.isSuccessful) {

                    expanses.value = response.body()!!
                    if (expanses.value.isEmpty()) {
                        isFound.value = false
                    } else isFound.value = true
                }

            } catch (e: Exception) {
                isSuccessful.value = false
            }
        }
    }


}