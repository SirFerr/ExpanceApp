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
import kotlinx.coroutines.withContext
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

    fun signIn(isSuccess: () -> Unit, isError: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = expanseAppApi.signIn(
                    Auth(
                        textUsername.value,
                        textUsername.value,
                        textPassword.value,
                    )
                )

                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.error == "") {
                            val receivedToken = it.token.trim()
                            token.value = receivedToken
                            tokenSharedPreferencesManager.saveToken(receivedToken)
                            isSuccessful.value = true
                            withContext(Dispatchers.Main) {
                                isSuccess()
                            }
                        } else {
                            isSuccessful.value = false
                            withContext(Dispatchers.Main) {
                                isError()
                            }
                        }
                    }
                } else {
                    isSuccessful.value = false
                    withContext(Dispatchers.Main) {
                        isError()
                    }
                }
            } catch (e: Exception) {
                isSuccessful.value = false
                withContext(Dispatchers.Main) {
                    isError()
                }
            }
        }
    }

}