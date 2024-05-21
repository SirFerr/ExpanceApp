package com.example.expanceapp.di

import com.example.expanceapp.data.local.TokenSharedPreferencesManager
import com.example.expanceapp.data.remote.AuthInterceptor
import com.example.expanceapp.data.remote.ExpanseAppApi
import com.example.expanceapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideExpanseAppApi(tokenSharedPreferencesManager: TokenSharedPreferencesManager): ExpanseAppApi {

        val authInterceptor = AuthInterceptor(tokenSharedPreferencesManager)

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ExpanseAppApi::class.java)
    }
}