package com.example.expanceapp.data.remote

data class Auth(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val token: String = "",
    val role: String = "USER",
    val error: String = ""

)
