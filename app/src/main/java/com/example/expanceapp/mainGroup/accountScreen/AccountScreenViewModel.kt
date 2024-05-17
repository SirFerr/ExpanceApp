package com.example.expanceapp.mainGroup.accountScreen

import androidx.lifecycle.ViewModel
import com.example.expanceapp.data.local.TokenSharedPreferencesManager
import com.example.expanceapp.data.remote.ExpanseAppApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountScreenViewModel @Inject constructor(
    private val expanseAppApi: ExpanseAppApi,
    private val tokenSharedPreferencesManager: TokenSharedPreferencesManager
) : ViewModel() {
    fun deleteToken() {
        tokenSharedPreferencesManager.deleteToken()
    }
}