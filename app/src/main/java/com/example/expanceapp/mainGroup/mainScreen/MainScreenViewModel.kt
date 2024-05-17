package com.example.expanceapp.mainGroup.mainScreen

import androidx.lifecycle.ViewModel
import com.example.expanceapp.data.remote.ExpanseAppApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val expanseAppApi: ExpanseAppApi,
) : ViewModel() {

    val isAdd = MutableStateFlow(false)
    val isOpenedTypes = MutableStateFlow(false)


    fun changeIsAdd() {
        isAdd.value = !isAdd.value
    }

    fun changeIsOpenedTypes() {
        isOpenedTypes.value = !isOpenedTypes.value
    }
}