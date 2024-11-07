package com.dag.nexq_app.presentation.userop.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

data class RadioButtonData(
    val text: Int,
    val expectedState: Int,
)

@Composable
fun RadioButton(
    shape: Shape, radioButtonData: RadioButtonData, buttonState: Int,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (buttonState == radioButtonData.expectedState) Color.Magenta else Color.Transparent,
        animationSpec = tween(durationMillis = 300),
        label = "" // Animation duration
    )
    val contentColor by animateColorAsState(
        targetValue = if (buttonState == radioButtonData.expectedState) Color.White else Color.Black,
        animationSpec = tween(durationMillis = 300),
        label = "" // Animation duration
    )
    OutlinedButton(
        modifier = Modifier, shape = shape, colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
        ), onClick = onClick
    ) {
        Text(
            text = stringResource(id = radioButtonData.text),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}