package com.example.expanceapp

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.expanceapp.scaffold.scaffold
import com.example.expanceapp.utils.Destinations
import com.example.expanceapp.loginGroup.loginScreen.LogInScreen
import com.example.expanceapp.loginGroup.signUpScreen.SignUpScreen
import com.example.expanceapp.mainGroup.accountScreen.AccountScreen
import com.example.expanceapp.mainGroup.mainScreen.MainScreen
import com.example.expanceapp.mainGroup.searchScreen.SearchScreen

@Composable
fun Navigation(): NavHostController {
    val focusManager = LocalFocusManager.current
    val firstNavController = rememberNavController()
    val duration = 700
    NavHost(navController = firstNavController,
        startDestination = Destinations.loginGroup,
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }) { focusManager.clearFocus() },
        enterTransition = { fadeIn(animationSpec = tween(duration)) },
        exitTransition = { fadeOut(animationSpec = tween(duration)) })
    {
        navigation(Destinations.login, Destinations.loginGroup) {
            composable(Destinations.login) { LogInScreen(firstNavController) }
            composable(Destinations.signUp) { SignUpScreen(firstNavController) }
        }
        composable(Destinations.mainGroup) {
            val navController = rememberNavController()
            scaffold(navController) {
                NavHost(
                    navController = navController,
                    startDestination = Destinations.main,
                    modifier = Modifier.padding(it),
                    enterTransition = { fadeIn(animationSpec = tween(duration)) },
                    exitTransition = { fadeOut(animationSpec = tween(duration)) }
                ) {
                    composable(Destinations.main) {
                        MainScreen(navController)
                    }
                    composable(Destinations.search) {
                        SearchScreen(navController)
                    }
                    composable(Destinations.account) {
                        AccountScreen(
                            navController,
                            firstNavController
                        )
                    }
                }
            }
        }
    }
    return firstNavController
}