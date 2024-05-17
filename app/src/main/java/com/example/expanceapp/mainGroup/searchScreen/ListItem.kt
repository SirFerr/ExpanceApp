package com.example.expanceapp.mainGroup.searchScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.expanceapp.R
import com.example.expanceapp.data.remote.Expanse


@Preview(showBackground = true)
@Composable
fun ListItem(expanse: Expanse = Expanse()) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.main_padding))
    ) {

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.main_padding))) {
                Text(text = expanse.value.toString())

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(text = expanse.name)
                    Text(text = expanse.type)
                }

            }

    }
}