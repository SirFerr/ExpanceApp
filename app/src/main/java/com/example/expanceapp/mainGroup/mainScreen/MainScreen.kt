package com.example.expanceapp.mainGroup.mainScreen

import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expanceapp.data.remote.Expanse
import com.example.expanceapp.utils.Destinations

@OptIn(ExperimentalMaterialApi::class)

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PieChartSection(pieChartData, viewModel::changeIsOpenedTypes)
            if (isOpenedTypes) {
                ExpenseTypesSection(pieChartData, navController)
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
            .fillMaxSize()
            .clickable { onChartClick() }
    )
}

@Composable
fun ExpenseTypesSection(pieChartData: List<PieChartData>, navController: NavHostController) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp
    ) {
        items(pieChartData) { data ->
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .clickable {

                        Log.d("click", data.name)
                        navController.navigate(Destinations.detailExpansesByType + "/${data.name}")
                    },
                border = BorderStroke(1.dp, data.color),
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
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
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
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
