package com.example.expanceapp.mainGroup.accountScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
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

    val list = listOf(

        AccountScreenCards("LogOut", Destinations.loginGroup, firstNavController)
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.main_padding))
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),

            ) {
            items(list) {
                AccountListCard(accountScreenCards = it, viewModel)
            }
        }
    }

}