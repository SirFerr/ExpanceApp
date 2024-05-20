package com.example.expanceapp.mainGroup.detailTypes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expanceapp.R
import com.example.expanceapp.mainGroup.ListItem
import com.example.expanceapp.mainGroup.searchScreen.CircularLoad
import com.example.expanceapp.mainGroup.searchScreen.ErrorNetworkCard

@Composable
fun DetailTypes(viewModel: DetailTypesViewModel = hiltViewModel(), type: String) {

    val expanses by viewModel.expanses.collectAsState()
    val isSuccessful by viewModel.isSuccessful.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isFound by viewModel.isFound.collectAsState()
    viewModel.getExpansesByType(type)
    Column(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.main_padding)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
    ) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = dimensionResource(id = R.dimen.main_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),
        ) {
            item {
                Text(text = type)
            }
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
                            ListItem(it)
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