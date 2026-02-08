package com.example.greychallenge.mvvm.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.greychallenge.mvvm.views.screens.HomeView
import com.example.greychallenge.mvvm.views.screens.StageScreen

fun NavGraphBuilder.homeNavGraph(
    navController: NavController
) {
    composable(HomeRoute.Home.route) {
        HomeView(
            onCheckProgress = {
                navController.navigate(HomeRoute.Path.route)
            }
        )
    }

    composable(HomeRoute.Path.route) {
        StageScreen(
            onBack = { navController.popBackStack() }
        )
    }
}
