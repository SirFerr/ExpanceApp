package com.example.expanceapp.mainGroup.detailTypes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expanceapp.R
import com.example.expanceapp.mainGroup.ExpanseItem
import com.example.expanceapp.mainGroup.generateColorFromType
import com.example.expanceapp.mainGroup.searchScreen.CircularLoad
import com.example.expanceapp.mainGroup.searchScreen.ErrorNetworkCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTypes(
    viewModel: DetailTypesViewModel = hiltViewModel(),
    type: String,
    navController: NavHostController
) {

    val expanses by viewModel.expanses.collectAsState()
    val isSuccessful by viewModel.isSuccessful.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isFound by viewModel.isFound.collectAsState()
    viewModel.getExpansesByType(type)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = type) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(generateColorFromType(type)),
            )
        },
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
        ) {
            LazyColumn(
                state = rememberLazyListState(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.main_padding)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),
            ) {
                if (!isLoading) {
                    if (isSuccessful) {
                        if (!isFound) {
                            item {
                                Text(
                                    text = stringResource(id = R.string.nothing_found),
                                    modifier = Modifier
                                        .padding(
                                            dimensionResource(id = R.dimen.main_padding)
                                        )
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleMedium
                                )

                            }
                        } else
                            items(expanses, key = { it.id }) {
                                ExpanseItem(it, navController)
                            }
                    } else {
                        item {
                            ErrorNetworkCard {
                            }
                        }
                    }
                } else {
                    item {
                        CircularLoad()
                    }
                }
                item { }
            }
        }
    }
}