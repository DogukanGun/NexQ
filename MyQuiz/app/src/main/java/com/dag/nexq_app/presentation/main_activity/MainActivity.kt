package com.dag.nexq_app.presentation.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dag.nexq_app.base.navigation.DefaultNavigationHost
import com.dag.nexq_app.base.navigation.DefaultNavigator
import com.dag.nexq_app.theme.MyQuizTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val defaultColor = Color.LightGray.toArgb()
    private val mainVM: MainVM by viewModels()

    @Inject
    lateinit var defaultNavigator: DefaultNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainVM.getColor()
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DefaultNavigationHost(
                        navigator = defaultNavigator
                    )
                }
            }
        }
    }
}