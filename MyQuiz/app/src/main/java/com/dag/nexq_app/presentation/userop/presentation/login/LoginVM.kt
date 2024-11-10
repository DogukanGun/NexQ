package com.dag.nexq_app.presentation.userop.presentation.login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.dag.nexq_app.BuildConfig.WEB_API_KEY
import com.dag.nexq_app.base.BaseVM
import com.dag.nexq_app.presentation.userop.presentation.components.RadioButtonData
import dagger.hilt.android.lifecycle.HiltViewModel
import com.dag.nexq_app.R
import com.dag.nexq_app.base.AlertDialogManager
import com.dag.nexq_app.base.navigation.DefaultNavigator
import com.dag.nexq_app.base.navigation.Destination
import com.dag.nexq_app.data.AlertDialogModel
import com.dag.nexq_app.domain.DataPreferencesStore
import com.dag.nexq_app.presentation.userop.domain.model.LoginRequest
import com.dag.nexq_app.presentation.userop.domain.model.RegisterRequest
import com.dag.nexq_app.presentation.userop.domain.usecase.LoginUseCase
import com.dag.nexq_app.presentation.userop.domain.usecase.RegisterUseCase
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val dataPreferencesStore: DataPreferencesStore,
    private val navigator: DefaultNavigator,
) : BaseVM<LoginVS>(LoginVS.Default) {

    val TAG = "LOGIN_VM"
    private val _buttonState = MutableStateFlow(R.string.userops_login_radio_button)
    val buttonState: StateFlow<Int> = _buttonState

    private fun generateNonce(): String {
        return UUID.randomUUID().toString()
    }

    fun onRadioButtonSelected(newSelection: Int) {
        _buttonState.value = newSelection
    }

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            loginUseCase.execute(loginRequest)
                .collectLatest {
                    dataPreferencesStore.write(DataPreferencesStore.TOKEN,it.token)
                    navigator.navigate(Destination.HomeGraph) {
                        this.popUpTo(0)
                    }
                }
        }
    }

    fun register(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            registerUseCase.execute(registerRequest)
                .collectLatest {

                }
        }
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        // Handle the successfully returned credential.
        val credential = result.credential

        when (credential) {

            // Passkey credential
            is PublicKeyCredential -> {
                // Share responseJson such as a GetCredentialResponse on your server to
                // validate and authenticate
                val responseJson = credential.authenticationResponseJson
            }

            // Password credential
            is PasswordCredential -> {
                // Send ID and password to your server to validate and authenticate.
                val username = credential.id
                val password = credential.password
            }

            // GoogleIdToken credential
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {

                        val googleIdTokenCredential: GoogleIdTokenCredential =
                            GoogleIdTokenCredential
                                .createFrom(credential.data)
                        //send this token to validate user
                        val id =
                            credential.data.getString("com.google.android.libraries.identity.googleid.BUNDLE_KEY_ID_TOKEN")
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e(TAG, "Received an invalid google id token response", e)
                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    Log.e(TAG, "Unexpected type of credential")
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                Log.e(TAG, "Unexpected type of credential")
            }
        }
    }

    fun useGoogleAuth(
        context: Context
    ) {
        val credentialManager = CredentialManager.create(context)
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(WEB_API_KEY)
            .setAutoSelectEnabled(true)
            .setFilterByAuthorizedAccounts(false)
            .setNonce(generateNonce())
            .build()
        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
        viewModelScope.launch {
            try {
                handleSignIn(
                    credentialManager.getCredential(
                        request = request,
                        context = context,
                    )
                )
            } catch (e: GetCredentialException) {
                Log.e(TAG, e.toString())
            }
        }
    }
}