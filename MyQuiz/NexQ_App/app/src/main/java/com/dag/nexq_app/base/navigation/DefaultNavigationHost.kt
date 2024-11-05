package com.dag.nexq_app.base.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.dag.nexq_app.presentation.onboard.Onboard
import com.dag.nexq_app.presentation.onboard.OnboardVM
import com.dag.nexq_app.presentation.userop.login.Login
import com.dag.nexq_app.presentation.userop.login.LoginVM

@Composable
fun DefaultNavigationHost(
    startDestination: Destination = Destination.Onboard,
    ) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable<Destination.Onboard> {
            val viewModel = hiltViewModel<OnboardVM>()
            Onboard(
                onboardVM = viewModel,
                navController = navController
            )
        }
        navigation<Destination.AuthGraph>(
            startDestination = Destination.LoginScreen
        ){
            composable<Destination.LoginScreen> { 
                val viewModel = hiltViewModel<LoginVM>()
                Login(
                    loginVM = viewModel
                )
            }
        }
    }
}