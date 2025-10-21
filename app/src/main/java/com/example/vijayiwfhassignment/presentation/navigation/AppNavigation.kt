package com.example.vijayiwfhassignment.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vijayiwfhassignment.presentation.screens.detail_screen.DetailsScreen
import com.example.vijayiwfhassignment.presentation.screens.detail_screen.DetailsViewModel
import com.example.vijayiwfhassignment.presentation.screens.home_screen.HomeScreen
import com.example.vijayiwfhassignment.presentation.screens.home_screen.HomeViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {

        composable("home") {

            val viewModel = koinViewModel<HomeViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            HomeScreen(
                state = state,
                onTitleClick = { titleId ->
                    navController.navigate("details/$titleId")
                }
            )
        }
        composable(
            route = "details/{titleId}",
            arguments = listOf(navArgument("titleId") { type = NavType.IntType })
        ) {

            val viewModel = koinViewModel<DetailsViewModel> {
                parametersOf(it.arguments?.getInt("titleId"))
            }
            val state by viewModel.state.collectAsStateWithLifecycle()

            DetailsScreen(state = state, onBackClick = { navController.popBackStack() })
        }
    }
}