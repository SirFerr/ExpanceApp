package com.example.expanceapp.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.expanceapp.scaffold.bottom.bottomNavigation
@Composable
fun scaffold(
    navController: NavHostController? = null, screen: @Composable (PaddingValues) -> Unit
) {
    Scaffold(content = {
        screen(it)
    }, bottomBar = {
        navController?.let { bottomNavigation(navController = it) }
    })
}