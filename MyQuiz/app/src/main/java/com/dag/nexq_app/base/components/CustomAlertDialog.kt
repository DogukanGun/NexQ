package com.dag.nexq_app.base.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.dag.nexq_app.base.navigation.DefaultNavigator
import com.dag.nexq_app.base.navigation.Destination
import com.dag.nexq_app.data.AlertDialogButton
import com.dag.nexq_app.data.AlertDialogButtonType
import com.dag.nexq_app.data.AlertDialogModel
import kotlinx.coroutines.launch

@Composable
fun CustomAlertDialog(
    alertDialogModel: AlertDialogModel,
    showAlert: MutableState<Boolean>,
    defaultNavigator: DefaultNavigator
) {
    Dialog(
        onDismissRequest = { showAlert.value = false },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        CustomAlert(
            alertDialogModel = alertDialogModel,
            showAlert = showAlert,
            defaultNavigator = defaultNavigator
        )
    }
}

@Composable
fun CustomAlert(
    alertDialogModel: AlertDialogModel,
    showAlert: MutableState<Boolean>,
    defaultNavigator: DefaultNavigator
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.padding(8.dp),
            shape = RoundedCornerShape(35.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    alertDialogModel.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    alertDialogModel.title,
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CustomButton(
                        backgroundColor = Color.Gray,
                        text = alertDialogModel.negativeButton.text
                    ) {
                        buttonOnClick(
                            alertDialogModel.negativeButton.type,
                            showAlert,
                            alertDialogModel.negativeButton.onClick,
                        ){
                            alertDialogModel.negativeButton.navigate?.let{
                                coroutineScope.launch {
                                    defaultNavigator.navigate(it)
                                }
                            }
                        }
                    }
                    CustomButton(
                        backgroundColor = Color.Red,
                        text = alertDialogModel.positiveButton.text
                    ) {
                        buttonOnClick(
                            alertDialogModel.positiveButton.type,
                            showAlert,
                            alertDialogModel.positiveButton.onClick,
                        ){
                            alertDialogModel.positiveButton.navigate?.let{
                                coroutineScope.launch {
                                    defaultNavigator.navigate(it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun buttonOnClick(
    buttonType: AlertDialogButtonType,
    showAlert: MutableState<Boolean>,
    onCustomButtonClick: (() -> Unit)? = null,
    onNavigationButtonClick: (() -> Unit)? = null,
) {
    when (buttonType) {
        AlertDialogButtonType.REFRESH -> {
            //TODO Implement later
        }
        AlertDialogButtonType.CLOSE -> {
            showAlert.value = false
        }
        AlertDialogButtonType.NAVIGATE -> {
            showAlert.value = false
            if (onNavigationButtonClick != null) {
                onNavigationButtonClick()
            }
        }
        AlertDialogButtonType.CUSTOM -> {
            showAlert.value = false
            if (onCustomButtonClick != null) {
                onCustomButtonClick()
            }
        }
    }
}


@Composable
@Preview
fun CustomAlertDialogPreview() {
    CustomAlertDialog(
        alertDialogModel = AlertDialogModel(
            title = "Test",
            message = "This is a test",
            positiveButton = AlertDialogButton(
                "positive button",
                {

                },
                null,
                AlertDialogButtonType.CLOSE
            ),
            negativeButton = AlertDialogButton(
                "positive button",
                {

                },
                null,
                AlertDialogButtonType.CLOSE
            )
        ),
        showAlert = mutableStateOf(false),
        defaultNavigator = DefaultNavigator(Destination.LoginScreen)
    )
}
