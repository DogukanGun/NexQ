package com.dag.nexq_app.presentation.userop.login

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dag.nexq_app.R
import com.dag.nexq_app.base.components.CustomButton
import com.dag.nexq_app.base.components.CustomTextField
import com.dag.nexq_app.presentation.userop.components.Divider
import com.dag.nexq_app.presentation.userop.components.GoogleButton
import com.dag.nexq_app.presentation.userop.components.RadioButton
import com.dag.nexq_app.presentation.userop.components.RadioButtonData

@Composable
fun Login(
    loginVM: LoginVM = hiltViewModel()
) {
    val buttonState = loginVM.buttonState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 128.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RadioButtons(loginVM = loginVM,buttonState = buttonState)
        UserInputFields(buttonState = buttonState)
        ActionButtons(loginVM = loginVM, context = context, buttonState = buttonState)
    }
}

@Composable
fun RadioButtons(loginVM: LoginVM ,buttonState: State<Int>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        RadioButton(
            shape = RoundedCornerShape(
                topStart = 8.dp, bottomStart = 8.dp,
                topEnd = 0.dp, bottomEnd = 0.dp
            ),
            radioButtonData = RadioButtonData(
                R.string.userops_login_radio_button,
                R.string.userops_login_radio_button
            ),
            buttonState = buttonState.value
        ) {
            loginVM.onRadioButtonSelected(R.string.userops_login_radio_button)
        }
        RadioButton(
            shape = RoundedCornerShape(
                topStart = 0.dp, bottomStart = 0.dp,
                topEnd = 8.dp, bottomEnd = 8.dp
            ),
            radioButtonData = RadioButtonData(
                R.string.userops_register_radio_button,
                R.string.userops_register_radio_button
            ),
            buttonState = buttonState.value
        ) {
            loginVM.onRadioButtonSelected(R.string.userops_register_radio_button)
        }
    }
}

@Composable
fun UserInputFields(buttonState: State<Int>) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        CustomTextField(
            label = stringResource(id = R.string.userops_email_textfield),
            onTextChange = {}
        )
        Spacer(modifier = Modifier.size(8.dp))
        if (buttonState.value == R.string.userops_register_radio_button) {
            CustomTextField(
                label = stringResource(id = R.string.userops_username_textfield),
                onTextChange = {}
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        CustomTextField(
            label = stringResource(id = R.string.userops_password_textfield),
            onTextChange = {},
            isPassword = true
        )
    }
}

@Composable
fun ActionButtons(
    loginVM: LoginVM,
    context: Context,
    buttonState: State<Int>
) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            backgroundColor = Color.Blue,
            text = if (buttonState.value == R.string.userops_login_radio_button)
                stringResource(id = R.string.userops_login_button) else
                stringResource(id = R.string.userops_register_button)
        ) {}
        Divider(
            modifier = Modifier.padding(16.dp),
            text = if (buttonState.value == R.string.userops_login_radio_button)
                stringResource(id = R.string.userops_divider_for_login) else
                stringResource(id = R.string.userops_divider_for_register)
        )
        GoogleButton(
            modifier = Modifier
        ) {
            loginVM.useGoogleAuth(context)
        }
    }
}

@Composable
@Preview
fun LoginPreview() {
    Login()
}
