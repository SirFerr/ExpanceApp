package com.example.expanceapp.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part

interface ExpanseAppApi {
    @GET("expanse/getAll")
    suspend fun getAll(): Response<List<Expanse>>

    @GET("expanse/getByName/{name}")
    suspend fun getByName(@Part("name") name: String): Response<Expanse>

    @POST("expanse/createExpanse")
    suspend fun createExpanse(@Body expanse: Expanse): Response<Expanse>
}