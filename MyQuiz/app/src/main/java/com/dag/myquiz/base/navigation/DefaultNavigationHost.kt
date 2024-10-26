package com.dag.myquiz.base.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dag.myquiz.presentation.onboard.Onboard
import com.dag.myquiz.presentation.onboard.OnboardVM

@Composable
fun DefaultNavigationHost(
    navController: NavHostController,
    startDestination: Destination = Destination.HomeScreen,

    ) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable<Destination.Onboard> {
            val viewModel = hiltViewModel<OnboardVM>()
            Onboard(
                onboardVM = viewModel
            )
        }
    }
}