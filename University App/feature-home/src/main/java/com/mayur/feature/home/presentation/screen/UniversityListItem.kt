package com.mayur.feature.home.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UniversityListItem(
    name: String,
    country: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)

            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Spacer(
                modifier = Modifier.width(16.dp)
            )
            Column {
                Text(text = name)
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                Text(text = country)
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            }
        }
    }
}