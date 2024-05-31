package com.example.expanceapp.mainGroup.detailExpanse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import com.example.expanceapp.R
import com.example.expanceapp.data.remote.Expanse
import com.example.expanceapp.mainGroup.generateColorFromType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailExpanse(navController: NavHostController, expanse: Expanse) {
    var isDelete by remember {
        mutableStateOf(false)
    }



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = expanse.type) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { isDelete = true }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(generateColorFromType(expanse.type))
            )
        },
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding) * 2)
            ) {
                Text(
                    text = expanse.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = expanse.description,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = expanse.value.toString() + " рублей",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
    }


    if (isDelete) {
        AlertDialog(
            title = { Text(text = "Delete expanse?") },
            onDismissRequest = { isDelete = false },
            dismissButton = {
                TextButton(onClick = { isDelete = false }) {
                    Text(text = "Dismiss")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    isDelete = false
                    navController.popBackStack()
                }) {
                    Text(text = "Confirm")
                }
            }
        )
    }
}


