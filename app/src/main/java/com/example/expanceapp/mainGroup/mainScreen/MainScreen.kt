package com.example.expanceapp.mainGroup.mainScreen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.HorizontalDivider
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
import com.example.expanceapp.utils.Destinations
import kotlin.random.Random

@OptIn(ExperimentalMaterialApi::class)

fun generateRandomExpanse(id: Int): Expanse {
    val randomNames = listOf("Rent", "Groceries", "Utilities", "Transport", "Entertainment")
    val randomDescriptions = listOf(
        "Monthly rent payment",
        "Weekly groceries",
        "Monthly utilities bill",
        "Daily commute costs",
        "Weekend entertainment"
    )
    val randomTypes = listOf("Fixed", "Variable", "Discretionary", "Non-discretionary")
    val randomValue = Random.nextInt(50, 5000)

    return Expanse(
        id = id,
        name = randomNames.random(),
        description = randomDescriptions.random(),
        type = randomTypes.random(),
        value = randomValue
    )
}

fun getRandomMonth(): String {
    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )
    return months.random()
}

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val isAdd by viewModel.isAdd.collectAsState()
    val isOpenedTypes by viewModel.isOpenedTypes.collectAsState()
    val pieChartData by viewModel.pieChartData.collectAsState()



    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.changeIsAdd() }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    ) { it ->
        it
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.main_padding)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),
        ) {
            item {
                PieChartSection(pieChartData, viewModel::changeIsOpenedTypes)

            }
            item {
                if (isOpenedTypes) {
                    ExpenseTypesSection(pieChartData, navController)
                }
            }
            val randomMonth = List(5) { getRandomMonth() }
            items(randomMonth) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(
                        dimensionResource(id = R.dimen.main_padding)
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            dimensionResource(id = R.dimen.main_padding)
                        )
                    ) {
                        Text(text = it)
                        HorizontalDivider()
                    }
                    val randomExpanses = List(20) { generateRandomExpanse(it + 1) }
                    randomExpanses.forEach(
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(
                                    dimensionResource(id = R.dimen.main_padding)
                                )
                            ) {
                                Spacer(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .background(
                                            color = viewModel.generateColorFromType(
                                                it.type
                                            )
                                        )
                                )
                                Text(text = it.name)

                            }
                            Text(text = it.value.toString() + " рублей")
                        }

                    }
                }


            }
        }

        if (isAdd) {
            AddExpenseDialog(
                viewModel::changeIsAdd,
                viewModel.expanseType.collectAsState().value,
                viewModel
            )
        }
    }
}

@Composable
fun PieChartSection(
    pieChartData: List<PieChartData>,
    onChartClick: () -> Unit
) {
    PieChart(
        data = pieChartData,
        modifier = Modifier
            .size(200.dp)
            .fillMaxWidth()
            .clickable { onChartClick() }
    )
}

@Composable
fun ExpenseTypesSection(pieChartData: List<PieChartData>, navController: NavHostController) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier.height(150.dp),
        columns = StaggeredGridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),
        verticalItemSpacing = dimensionResource(id = R.dimen.main_padding)
    ) {
        items(pieChartData) { data ->
            Card(
                modifier = Modifier
                    .clickable {
                        Log.d("click", data.name)
                        navController.navigate(Destinations.detailExpansesByType + "/${data.name}")
                    },
                border = BorderStroke(1.dp, data.color),
            ) {
                Row(
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.main_padding)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(text = data.name)
                }
            }
        }
    }
}

@Composable
fun AddExpenseDialog(
    onDismiss: () -> Unit,
    expanseTypes: List<String>,
    viewModel: MainScreenViewModel
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
    var expanseTypeSelected by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }

    AlertDialog(
        title = { Text(text = "Add new expanse") },
        onDismissRequest = { onDismiss() },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "Dismiss")
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onDismiss()
                viewModel.createExpanse(
                    Expanse(
                        name = name,
                        description = description,
                        value = value.toInt(),
                        type = expanseTypeSelected
                    )
                )
            }) {
                Text(text = "Confirm")
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "Name") }
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(text = "Description") }
                )
                OutlinedTextField(
                    value = value,
                    onValueChange = { value = it },
                    label = { Text(text = "Value") }
                )
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    onClick = { isExpanded = true }
                ) {
                    Text(text = if (expanseTypeSelected.isNotEmpty()) expanseTypeSelected else "Select type")
                }
                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    expanseTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(text = type) },
                            onClick = {
                                expanseTypeSelected = type
                                isExpanded = false
                            }
                        )
                    }
                }
            }
        }
    )
}
