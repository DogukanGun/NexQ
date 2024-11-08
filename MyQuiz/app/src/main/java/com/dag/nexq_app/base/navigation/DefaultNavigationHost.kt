package com.dag.nexq_app.base.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.dag.nexq_app.base.extensions.ObserveAsEvents
import com.dag.nexq_app.presentation.home.presentation.Home
import com.dag.nexq_app.presentation.onboard.Onboard
import com.dag.nexq_app.presentation.onboard.OnboardVM
import com.dag.nexq_app.presentation.splash.Splash
import com.dag.nexq_app.presentation.splash.SplashVM
import com.dag.nexq_app.presentation.userop.presentation.login.Login
import com.dag.nexq_app.presentation.userop.presentation.login.LoginVM
import kotlinx.coroutines.flow.Flow

@Composable
fun DefaultNavigationHost(
    startDestination: Destination = Destination.Splash,
    navigator: DefaultNavigator,
    modifier: Modifier = Modifier,
    navBackStackEntryState: (NavBackStackEntry) -> Unit

) {
    val navController = rememberNavController()
    ObserveAsEvents(flow = navigator.navigationActions) { action ->
        when (action) {
            is NavigationAction.Navigate -> navController.navigate(
                action.destination
            ) {
                action.navOptions(this)
            }
            NavigationAction.NavigateUp -> navController.navigateUp()
        }
    }
    ObserveAsEvents(flow = navController.currentBackStackEntryFlow){
        navBackStackEntryState(it)
    }
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {
        composable<Destination.Splash> {
            val viewModel = hiltViewModel<SplashVM>()
            Splash(
                splashVM = viewModel
            )
        }
        composable<Destination.Onboard> {
            val viewModel = hiltViewModel<OnboardVM>()
            Onboard(
                onboardVM = viewModel,
                navController = navController
            )
        }
        composable<Destination.LoginScreen> {
            val viewModel = hiltViewModel<LoginVM>()
            Login(
                loginVM = viewModel
            )
        }
        navigation<Destination.HomeGraph>(
            startDestination = Destination.HomeScreen
        ) {
            composable<Destination.HomeScreen> {
                Home()
            }
        }
    }
}