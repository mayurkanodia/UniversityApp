package com.mayur.testapplicatiom.test.prasentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UserItem(
    email: String,
    name : String,
    phone : String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
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
                Text(text = email)
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                Text(text = phone)
            }
        }
    }
}