package com.mayur.feature.home.presentation.screen

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mayur.core.base.UiEvent
import com.mayur.core.base.navigation.LocalNavController
import com.mayur.feature.detail.presentation.DetailActivity
import com.mayur.feature.home.domain.model.University
import com.mayur.feature.home.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    val navController = LocalNavController.current
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val snackbarHostState =
        remember { SnackbarHostState() }

    var selectedUniversityName by remember {
        mutableStateOf<String?>(null)
    }

    var selectedUniversityCountry by remember {
        mutableStateOf("")
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            val refresh =
                result.data?.getBooleanExtra(
                    "refresh",
                    false
                ) ?: false

            if (refresh) {
                viewModel.refresh()
            }
        }


    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowToast -> {
                    Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        event.message
                    )
                }

                is UiEvent.Navigate -> {
                    navController.navigate(
                        event.route
                    )
                }

                is UiEvent.NavigateBack -> {
                    navController.popBackStack()
                }

                is UiEvent.ShowProductDialog -> {
                    selectedUniversityName = event.name
                    selectedUniversityCountry = event.country
                    showDialog = true
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("home_screen")
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            when {
                uiState.isLoading -> {
                    LoadingView()
                }

                uiState.error != null -> {
                    ErrorView(uiState.error!!, { viewModel.refresh() })
                }

                uiState.universities.isEmpty() -> {
                    EmptyView()
                }

                else -> {
                    UniversityListView(
                        uiState.universities
                    ) { viewModel.onItemClick(it.name, it.country) }
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(
                Alignment.BottomCenter
            )
        )

        if (showDialog &&
            selectedUniversityName != null
        ) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                },
                title = {
                    Text(
                        "Details"
                    )
                },
                text = {
                    Text(
                        "Open details for $selectedUniversityName?"
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialog = false
                            //viewModel.openProductDetail(selectedUniversityName!!)
                            launcher.launch(
                                Intent(
                                    context,
                                    DetailActivity::class.java
                                ).apply {
                                    putExtra(
                                        "name",
                                        selectedUniversityName
                                    )
                                }
                            )
                        }
                    ) {
                        Text(
                            stringResource(
                                com.mayur.core.ui.R.string.open
                            )
                        )
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showDialog = false
                        }
                    ) {
                        Text(
                            stringResource(
                                com.mayur.core.ui.R.string.cancel
                            )
                        )
                    }
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UniversityListView(
    universities: List<University>,
    onProductClick: (University) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("University App")
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(universities) { university ->
                UniversityListItem(
                    name = university.name,
                    country = university.country,
                    onClick = {
                        onProductClick(university)
                    }
                )
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
    message: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(message)
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Button(
                onClick
            ) {
                Text(
                    stringResource(
                        com.mayur.core.ui.R.string.retry
                    )
                )
            }
        }
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


