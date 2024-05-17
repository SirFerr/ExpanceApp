package com.example.expanceapp.mainGroup.mainScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expanceapp.R
import com.example.expanceapp.data.remote.Expanse
import com.example.expanceapp.utils.ExpanseType

@OptIn(ExperimentalMaterialApi::class)

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {

    val isAdd by viewModel.isAdd.collectAsState()
    val isOpenedTypes by viewModel.isOpenedTypes.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
        FloatingActionButton(onClick = { viewModel.changeIsAdd() }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = null)
        }

    }) { it ->
        it

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.main_padding)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
        ) {
            val expanses = listOf(
                Expanse(
                    id = 1,
                    name = "Food",
                    description = "Lunch",
                    type = "Food",
                    value = 300
                ),
                Expanse(
                    id = 2,
                    name = "Bus",
                    description = "Transport",
                    type = "Transport",
                    value = 20
                ),
                Expanse(
                    id = 3,
                    name = "Movie",
                    description = "Cinema",
                    type = "Entertainment",
                    value = 10
                ),
                Expanse(
                    id = 4,
                    name = "Groceries",
                    description = "Shopping",
                    type = "Clothes",
                    value = 40
                )
            )

            val pieChartData = mapExpanseToPieChartData(expanses)

            PieChart(
                data = pieChartData,
                modifier = Modifier
                    .size(200.dp)
                    .fillMaxSize()
                    .clickable {
                        viewModel.changeIsOpenedTypes()
                    }
            )
            if (isOpenedTypes)
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),
                    verticalItemSpacing = dimensionResource(id = R.dimen.main_padding)
                ) {
                    items(ExpanseType.getAllTypes()) {
                        Card(
                            modifier = Modifier
                                .wrapContentSize(),
                            border = BorderStroke(1.dp, it.color),
                        ) {
                            Row(
                                modifier = Modifier.padding(dimensionResource(id = R.dimen.main_padding)),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
                            ) {
                                Text(text = it.displayName)
                            }
                        }

                    }
                }

        }

        if (isAdd) {
            var name by remember {
                mutableStateOf("")
            }
            var description by remember {
                mutableStateOf("")
            }
            var value by remember {
                mutableStateOf("")
            }
            var expanseType by remember {
                mutableStateOf("")
            }
            var expanseTypes = ExpanseType.getAllTypes()
            var isExpanded by remember {
                mutableStateOf(false)
            }
            AlertDialog(
                onDismissRequest = { },
                dismissButton = {
                    TextButton(onClick = {
                        viewModel.changeIsAdd()
                    }) {
                        Text(text = "dismiss")
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.changeIsAdd()
                    }) {
                        Text(text = "confirm")
                    }
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))) {
                        OutlinedTextField(value = name, onValueChange = { name = it }, label = {
                            Text(
                                text = "Name"
                            )
                        })
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = {
                                Text(
                                    text = "Description"
                                )
                            })
                        OutlinedTextField(value = value, onValueChange = { value = it }, label = {
                            Text(
                                text = "Value"
                            )
                        })

                        Button(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(
                            dimensionResource(id = R.dimen.rounded_corner)
                        ), onClick = { isExpanded = true }) {
                            Text(text = "Type" + if (expanseType != "") " $expanseType expanses" else "")
                        }
                        DropdownMenu(
                            expanded = isExpanded, onDismissRequest = {
                                isExpanded = false
                            }) {
                            expanseTypes.forEach {
                                DropdownMenuItem(text = { Text(text = it.displayName) },
                                    onClick = {
                                        isExpanded = false
                                        expanseType = it.displayName
                                    })
                            }
                        }
                    }
                })
        }
    }
}