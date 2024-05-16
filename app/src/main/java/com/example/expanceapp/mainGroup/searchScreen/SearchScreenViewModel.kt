package com.example.expanceapp.view.mainGroup.searchScreen

import androidx.lifecycle.ViewModel
import com.example.expanceapp.data.local.SearchHistorySharedPreferencesManager
import com.example.expanceapp.data.remote.ExpanseAppApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val expanseAppApi: ExpanseAppApi,
    private val searchHistorySharedPreferencesManager: SearchHistorySharedPreferencesManager
) : ViewModel() {
}