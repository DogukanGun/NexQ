package com.dag.nexq_app.base

import com.dag.nexq_app.data.AlertDialogModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class AlertDialogManager {
    private val _alertFlow = MutableSharedFlow<AlertDialogModel>(replay = 0)
    val alertFlow: SharedFlow<AlertDialogModel> = _alertFlow

    suspend fun showAlert(alertDialogModel: AlertDialogModel) {
        _alertFlow.emit(alertDialogModel)
    }
}