package com.dag.nexq_app.presentation.mining

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dag.nexq_app.presentation.mainActivity.MainVM
import com.dag.nexq_app.presentation.mining.ui.theme.NexQ_AppTheme
import com.solana.mobilewalletadapter.clientlib.ActivityResultSender
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MiningActivity : ComponentActivity() {

    private val miningVM: MiningVM by viewModels()

    private lateinit var activityResultSender: ActivityResultSender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityResultSender = ActivityResultSender(this)
        miningVM.startMining(
            this as Context,
            activityResultSender
        )
        setContent {
            val state = miningVM.viewState.collectAsState()
            if (state.value is MiningState.ReturnResult){
                val resultIntent = Intent()
                resultIntent.putExtra("address", (state.value as MiningState.ReturnResult).address)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
            NexQ_AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NexQ_AppTheme {
        Greeting("Android")
    }
}