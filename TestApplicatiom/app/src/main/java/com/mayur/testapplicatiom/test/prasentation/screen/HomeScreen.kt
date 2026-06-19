package com.mayur.testapplicatiom.test.prasentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mayur.testapplicatiom.test.domain.model.User
import com.mayur.testapplicatiom.test.prasentation.states.UserUiState
import com.mayur.testapplicatiom.test.prasentation.viewmodel.UserViewModel

@Composable
fun HomeScreen(
    viewModel: UserViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    ProductList(uiState)
}

@Composable
private fun ProductList(uiState: UserUiState ) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 2.dp)
            .testTag("home_screen")
    ) {

        when {
            uiState.isLoading -> {
                LoadingView()
            }
            uiState.error != null -> {
                ErrorView(uiState.error)
            }
            uiState.products.isEmpty() -> {
                EmptyView()
            }
            else -> {
                ProductList(uiState.products)
            }
        }
    }
}




@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorView(
    message: String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(message)
    }
}

@Composable
private fun EmptyView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("NO ITEMS FOUND")
    }
}

@Composable
private fun ProductList(users: List<User>,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(users) { user ->
            UserItem(
                email = user.email,
                name = user.name,
                phone = user.phone
            )
        }
    }
}

