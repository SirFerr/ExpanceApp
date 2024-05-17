package com.example.expanceapp.utils

import androidx.compose.ui.graphics.Color

object Constants {

    const val BASE_URL = "http://localhost:8080/"

}

object Destinations {
    const val loginGroup = "loginGroup"
    const val login = "logIn"
    const val signUp = "signUp"
    const val mainGroup = "mainGroup"
    const val main = "main"
    const val search = "search"
    const val account = "account"
}

sealed class ExpanseType(val displayName: String, val color: Color) {
    object Food : ExpanseType("Food", Color(0xFF8BC34A))
    object Clothes : ExpanseType("Clothes", Color(0xFF4FC3F7))
    object Domestic : ExpanseType("Domestic", Color(0xFF00ACC1))
    object Entertainment : ExpanseType("Entertainment", Color(0xFFFFEB3B))
    object Transport : ExpanseType("Transport", Color(0xFFFF9800))
    object Health : ExpanseType("Health", Color(0xFFE91E63))
    companion object {
        fun fromDisplayName(displayName: String): ExpanseType? {
            return when (displayName) {
                Food.displayName -> Food
                Clothes.displayName -> Clothes
                Domestic.displayName -> Domestic
                Entertainment.displayName -> Entertainment
                Transport.displayName -> Transport
                Health.displayName -> Health
                else -> null
            }
        }

        fun getAllTypes(): List<ExpanseType> {
            return listOf(Food, Clothes, Domestic, Entertainment, Transport, Health)
        }
    }
}