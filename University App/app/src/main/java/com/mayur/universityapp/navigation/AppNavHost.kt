package com.mayur.universityapp.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mayur.core.base.navigation.AppRoutes
import com.mayur.core.base.navigation.LocalNavController
import com.mayur.feature.home.presentation.screen.HomeScreen


@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavHost(
            navController = navController,
            startDestination = AppRoutes.HOME
        ) {
            composable(AppRoutes.HOME) {
                HomeScreen()
            }
            composable(
                route = AppRoutes.DETAIL_ROUTE,
                arguments = listOf(
                    navArgument("universityName") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val universityName =
                    backStackEntry.arguments?.getString(
                        "universityName", "No Data Found"
                    ) ?: "No Data Found"

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "University Detail: $universityName"
                        )
                    }
                }
            }
        }
    }
}