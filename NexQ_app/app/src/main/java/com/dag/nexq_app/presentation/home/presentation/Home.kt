package com.dag.nexq_app.presentation.home.presentation

import android.Manifest
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dag.nexq_app.R
import com.dag.nexq_app.presentation.home.components.HomeCard
import com.dag.nexq_app.presentation.home.components.HomeHeader
import com.dag.nexq_app.presentation.mining.MiningActivity
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Home(
    homeVM: HomeVM = hiltViewModel()
) {
    var miningRequested by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            it.data?.getStringExtra("address")?.let { wallet ->
                homeVM.saveWallet(wallet)
            }
        }
    )
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    val context = LocalContext.current
    LaunchedEffect(permissionsState.allPermissionsGranted && miningRequested) {
        if (permissionsState.allPermissionsGranted && miningRequested) {
            launcher.launch(Intent(context, MiningActivity::class.java))
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        HomeHeader()
        HomeCard(
            text = stringResource(id = R.string.home_screen_create_quiz),
            customButtonText = stringResource(id = R.string.home_screen_create_quiz_button)
        ) {
            homeVM.joinQuiz()
        }
        HomeCard(
            text = stringResource(id = R.string.home_screen_start_mining),
            customButtonText = stringResource(id = R.string.home_screen_start_mining_button)
        ) {
            miningRequested = true
            permissionsState.launchMultiplePermissionRequest()
        }
    }
}


@Composable
@Preview
fun HomePreview() {
    Home()
}