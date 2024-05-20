package com.example.expanceapp.mainGroup.searchScreen

import androidx.lifecycle.ViewModel
import com.example.expanceapp.data.local.SearchHistorySharedPreferencesManager
import com.example.expanceapp.data.remote.Expanse
import com.example.expanceapp.data.remote.ExpanseAppApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val expanseAppApi: ExpanseAppApi,
    private val searchHistorySharedPreferencesManager: SearchHistorySharedPreferencesManager
) : ViewModel() {


    val expanses = MutableStateFlow<List<Expanse>>(listOf())
    val searchText = MutableStateFlow("")


    var isLoading = MutableStateFlow(false)
    val isSearching = MutableStateFlow(false)
    val isSuccessful = MutableStateFlow(true)
    val isFound = MutableStateFlow(true)

    val searchHistory = MutableStateFlow<List<String>>(listOf())

    init {
        searchHistory.value = searchHistorySharedPreferencesManager.getLastTenStrings()
//        val timer = Timer("schedule", true)
//        timer.scheduleAtFixedRate(2000, 2000) {
//            search()
//        }
    }

    fun search() {
        if (searchText.value != "") {
            searchHistorySharedPreferencesManager.saveString(value = searchText.value)
            getByName()
        }
        searchHistory.value = searchHistorySharedPreferencesManager.getLastTenStrings()
        isSearching.value = false

    }

    fun getByName() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                isLoading.value = true
                val response = expanseAppApi.getByName(
                    searchText.value,

                    )
                isSuccessful.value = response.isSuccessful
                if (response.isSuccessful) {
                    expanses.value = response.body()!!
                    isFound.value = response.body()!! != listOf<Expanse>()
                }
            } catch (e: Exception) {
                isSuccessful.value = false
            } finally {
                isLoading.value = false
            }
        }
    }
}