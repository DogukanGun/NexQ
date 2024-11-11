package com.dag.nexq_app.presentation.mainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.dag.nexq_app.base.AlertDialogManager
import com.dag.nexq_app.base.components.CustomAlertDialog
import com.dag.nexq_app.base.components.Visibility
import com.dag.nexq_app.base.navigation.DefaultNavigationHost
import com.dag.nexq_app.base.navigation.DefaultNavigator
import com.dag.nexq_app.base.navigation.Destination
import com.dag.nexq_app.data.AlertDialogModel
import com.dag.nexq_app.presentation.mainActivity.components.bottom_navbar.BottomNavbar
import com.dag.nexq_app.presentation.mainActivity.components.bottom_navbar.NavItem
import com.dag.nexq_app.theme.MyQuizTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val defaultColor = Color.LightGray.toArgb()
    private val mainVM: MainVM by viewModels()

    private val currentRoute = mutableStateOf<String?>(null)

    @Inject
    lateinit var defaultNavigator: DefaultNavigator

    @Inject
    lateinit var alertDialogManager: AlertDialogManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainVM.getColor()
        val showAlert = mutableStateOf(false)
        val alertDialogModel = mutableStateOf<AlertDialogModel?>(null)

        if (lifecycleScope.isActive) {
            lifecycleScope.launch {
                alertDialogManager.alertFlow.collect { model ->
                    alertDialogModel.value = model
                    showAlert.value = true
                }
            }
        }
        setContent {
            val viewState = mainVM.viewState.collectAsStateWithLifecycle()
            val statusBarColor = if (viewState.value is MainVS.ColorChanged) {
                (viewState.value as MainVS.ColorChanged).color
            } else {
                defaultColor
            }
            MyQuizTheme(
                statusBarColor = statusBarColor
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            DefaultNavigationHost(
                                navigator = defaultNavigator,
                                modifier = Modifier.weight(1f)
                            ) {
                                currentRoute.value = it.destination.route
                                    ?.split(".")?.last()
                            }
                            if (mainVM.isBottomNavActive(currentRoute.value)) {
                                BottomNavbar(
                                    items = NavItem.values().asList(),
                                    currentDestination = currentRoute.value
                                        ?: Destination.HomeScreen.toString(),
                                    onClick = {
                                        mainVM.navigate(it)
                                    }
                                )
                            }
                        }
                    }
                    Visibility(case = showAlert.value && alertDialogModel.value != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.5f))
                                .blur(16.dp)
                                .zIndex(10f)
                        ) {
                            CustomAlertDialog(
                                alertDialogModel = alertDialogModel.value!!,
                                showAlert = showAlert,
                                defaultNavigator = defaultNavigator
                            )
                        }
                    }
                }
            }
        }
    }
}