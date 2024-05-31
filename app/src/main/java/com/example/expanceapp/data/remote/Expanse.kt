package com.example.expanceapp.data.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Expanse(
    val id: Int = -1,
    val name: String = "name",
    val description: String = "description",
    val type: String = "type",
    val value: Int = 0
) : Parcelable
