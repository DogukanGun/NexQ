package com.dag.nexq_app.presentation.mining

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.dag.nexq_app.base.BaseVM
import com.dag.nexq_app.blockchain.SearchWorkManager
import com.solana.mobilewalletadapter.clientlib.ActivityResultSender
import com.solana.mobilewalletadapter.clientlib.ConnectionIdentity
import com.solana.mobilewalletadapter.clientlib.MobileWalletAdapter
import com.solana.mobilewalletadapter.clientlib.TransactionResult
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MiningVM: BaseVM<MiningState>(MiningState.Default) {

    @SuppressLint("RestrictedApi")
    fun startMining(
        context: Context,
        sender: ActivityResultSender
    ) {
        val solanaUri = Uri.parse("https://yourdapp.com")
        val iconUri =
            Uri.parse("favicon.ico")
        val identityName = "Solana Kotlin dApp"
        // Construct the client
        val walletAdapter = MobileWalletAdapter(
            connectionIdentity = ConnectionIdentity(
                identityUri = solanaUri,
                iconUri = iconUri,
                identityName = identityName
            )
        )
        if (walletAdapter.authToken == null) {
            connectWallet(walletAdapter,sender,context)
        } else {
            println("authToken is not null")
        }
    }

    private fun connectWallet(walletAdapter: MobileWalletAdapter, sender: ActivityResultSender, context: Context){
        viewModelScope.launch {
            when (val result = walletAdapter.connect(sender)) {
                is TransactionResult.Success -> {
                    // On success, an `AuthorizationResult` type is returned.
                    val authResult = result.authResult
                    val saveRequest =
                        PeriodicWorkRequestBuilder<SearchWorkManager>(1, TimeUnit.HOURS)
                            .setInputData(
                                workDataOf(
                                    "wallet_key" to authResult.accounts[0].publicKey
                                )
                            )
                            .build()
                    WorkManager
                        .getInstance(context)
                        .enqueue(saveRequest)
                    _viewState.value = MiningState.ReturnResult(authResult.accounts[0].publicKey.toString())
                }

                is TransactionResult.NoWalletFound -> {
                    println("No MWA compatible wallet app found on device.")
                }

                is TransactionResult.Failure -> {
                    println("Error connecting to wallet: " + result.e.message)
                }

                else -> {}
            }

        }
    }

}