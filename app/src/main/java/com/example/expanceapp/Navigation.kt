@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.expanceapp

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.expanceapp.loginGroup.loginScreen.LogInScreen
import com.example.expanceapp.loginGroup.signUpScreen.SignUpScreen
import com.example.expanceapp.mainGroup.accountScreen.AccountScreen
import com.example.expanceapp.mainGroup.detailTypes.DetailTypes
import com.example.expanceapp.mainGroup.mainScreen.MainScreen
import com.example.expanceapp.mainGroup.searchScreen.SearchScreen
import com.example.expanceapp.scaffold.scaffold
import com.example.expanceapp.utils.Destinations

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
                    composable(
                        Destinations.detailExpansesByType + "/{type}",
                        listOf(
                            navArgument("type")
                            {
                                type = NavType.StringType
                            })
                    ) { backStackEntry ->
                        val type = backStackEntry.arguments?.getString("type")
                        if (type != null) {
                            DetailTypes(type = type)
                        }

                    }
                }
            }
        }
    }
    return firstNavController
}