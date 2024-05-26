package com.example.expanceapp.mainGroup.accountScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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

    val greetings = listOf(
        "Привет",  // Русский
        "Hello",   // Английский
        "Hola",    // Испанский
        "Bonjour", // Французский
        "Hallo",   // Немецкий
        "Ciao",    // Итальянский
        "Olá",     // Португальский
        "こんにちは", // Японский
        "你好",     // Китайский (упрощенный)
        "안녕하세요", // Корейский
        "سلام",     // Арабский
        "नमस्ते",   // Хинди
        "สวัสดี",   // Тайский
        "Merhaba", // Турецкий
        "Γειά",    // Греческий
        "שָׁלוֹם",  // Иврит
        "नमस्कार",  // Маратхи
        "Bună",    // Румынский
        "Tere"     // Эстонский
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

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.main_padding)
            )
        ) {
            items(greetings) {
                Text(
                    text = "$it User",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }


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
                    Text(text = stringResource(id = R.string.dismiss))
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
                    Text(text = stringResource(id = R.string.confirm))
                }
            }, text = {
                Text(text = "Are you sure you want to logOut?")
            })

    }

}