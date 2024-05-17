package com.example.expanceapp.mainGroup.accountScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expanceapp.R
import com.example.expanceapp.utils.Destinations

@Composable
fun AccountScreen(
    navController: NavHostController,
    firstNavController: NavHostController,
    viewModel: AccountScreenViewModel = hiltViewModel()
) {
    var isLogOut by remember {
        mutableStateOf(false)
    }
    val list = listOf(

        AccountScreenCards("LogOut", Destinations.loginGroup, firstNavController)
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.main_padding))
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            IconButton(onClick = { isLogOut = true }) {
                Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = null)
            }

        }

        Text(text = "Name")
        Text(text = "Email")


    }
    if (isLogOut) {
        AlertDialog(
            onDismissRequest = {
                isLogOut = false
            },
            dismissButton = {
                TextButton(onClick = {
                    isLogOut = false
                }) {
                    Text(text = "dismiss")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    isLogOut = false
                    viewModel.deleteToken()
                    firstNavController.navigate(Destinations.loginGroup) {
                        popUpTo("main") { inclusive = true }
                    }
                }) {
                    Text(text = "confirm")
                }
            }, text = {
                Text(text = "Are you sure you want to logOut?")
            })

    }

}