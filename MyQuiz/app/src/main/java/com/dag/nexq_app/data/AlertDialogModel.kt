package com.dag.nexq_app.data

import com.dag.nexq_app.base.navigation.Destination

data class AlertDialogModel(
    val title: String,
    val message: String,
    val positiveButton: AlertDialogButton,
    val negativeButton: AlertDialogButton? = null,
)

data class AlertDialogButton(
    val text:String,
    val onClick:(() -> Unit)? = null,
    val navigate: Destination? = null,
    val type: AlertDialogButtonType
)


enum class AlertDialogButtonType {
    REFRESH,
    NAVIGATE,
    CLOSE,
    CUSTOM
}