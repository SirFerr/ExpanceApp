package com.example.expanceapp.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ExpanseAppApi {
    @GET("expanse/getAll")
    suspend fun getAll(): Response<List<Expanse>>

    @GET("expanse/getByName/{name}")
    suspend fun getByName(@Path("name") name: String): Response<List<Expanse>>

    @POST("expanse/createExpanse")
    suspend fun createExpanse(@Body expanse: Expanse): Response<Expanse>

    @POST("auth/signup")
    suspend fun signUp(@Body auth: Auth): Response<Auth>

    @POST("auth/signin")
    suspend fun signIn(@Body auth: Auth): Response<Auth>

    @GET("/expanse/categories/getAllTypes")
    suspend fun getAllTypes(): Response<List<String>>

    @GET("/expanse/categories/getAllExpansesAmountByType")
    suspend fun getExpansesAmountByType(): Response<Map<String, Int>>

    @GET("/expanse/categories/getExpansesByType/{type}")
    suspend fun getExpansesByType(@Path("type") type: String): Response<List<Expanse>>
}