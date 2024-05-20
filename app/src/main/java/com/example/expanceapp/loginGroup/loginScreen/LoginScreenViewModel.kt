package com.example.expanceapp.loginGroup.loginScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.expanceapp.data.local.TokenSharedPreferencesManager
import com.example.expanceapp.data.remote.Auth
import com.example.expanceapp.data.remote.ExpanseAppApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val tokenSharedPreferencesManager: TokenSharedPreferencesManager,
    private val expanseAppApi: ExpanseAppApi
) : ViewModel() {
    var textUsername = MutableStateFlow("")
    var textPassword = MutableStateFlow("")
    val token = MutableStateFlow("")
    val isSuccessful = MutableStateFlow(false)

    init {
        token.value = tokenSharedPreferencesManager.getToken()
    }

    fun signIn() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = expanseAppApi.signIn(
                    Auth(
                        textUsername.value,
                        textUsername.value,
                        textPassword.value,
                    )
                )
                isSuccessful.value = response.isSuccessful
                if (response.isSuccessful) {
                    response.body()?.let {
                        val receivedToken = it.token.trim()
                        Log.d("LoginScreenViewModel", "Received token: '$receivedToken'")
                        token.value = receivedToken
                        saveToken(receivedToken)
                    }
                } else {
                    isSuccessful.value = false
                }
            } catch (e: Exception) {
                Log.e("signIn", e.message.toString())
                isSuccessful.value = false
            }
        }
    }

    private fun saveToken(token: String) {
        tokenSharedPreferencesManager.saveToken(token)
    }
}