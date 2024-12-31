package com.dag.nexq_app.presentation.userop.presentation.login

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.credentials.CreateCredentialResponse
import androidx.credentials.CreatePublicKeyCredentialRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.GetPasswordOption
import androidx.credentials.GetPublicKeyCredentialOption
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.CreateCredentialCustomException
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.CreateCredentialInterruptedException
import androidx.credentials.exceptions.CreateCredentialProviderConfigurationException
import androidx.credentials.exceptions.CreateCredentialUnknownException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.publickeycredential.CreatePublicKeyCredentialDomException
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
import com.dag.nexq_app.data.AlertDialogButton
import com.dag.nexq_app.data.AlertDialogButtonType
import com.dag.nexq_app.data.AlertDialogModel
import com.dag.nexq_app.domain.DataPreferencesStore
import com.dag.nexq_app.presentation.userop.domain.model.LoginRequest
import com.dag.nexq_app.presentation.userop.domain.model.RegisterRequest
import com.dag.nexq_app.presentation.userop.domain.model.UserOpResponse
import com.dag.nexq_app.presentation.userop.domain.usecase.LoginUseCase
import com.dag.nexq_app.presentation.userop.domain.usecase.RegisterPasskeyUseCase
import com.dag.nexq_app.presentation.userop.domain.usecase.RegisterUseCase
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val alertDialogManager: AlertDialogManager,
    private val dataPreferencesStore: DataPreferencesStore,
    private val registerPasskeyUseCase: RegisterPasskeyUseCase,
    @ApplicationContext private val context: Context,
    private val navigator: DefaultNavigator,
) : BaseVM<LoginVS>(LoginVS.Default) {

    private val TAG = "LOGIN_VM"
    private val _buttonState = MutableStateFlow(R.string.userops_login_radio_button)
    private val _createPubkey = MutableStateFlow(false)
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
                    handleAuthToken(it)
                }
        }
    }

    fun register(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            registerUseCase.execute(registerRequest)
                .collectLatest { token ->
                    alertDialogManager.showAlert(
                        AlertDialogModel(
                            "Do you want to create Passkey?",
                            "You can login with passkey faster.",
                            positiveButton = AlertDialogButton(
                                "Create",
                                type = AlertDialogButtonType.CUSTOM,
                                onClick = {
                                    _createPubkey.value = true
                                }
                            ),
                            negativeButton = AlertDialogButton(
                                "Close",
                                type = AlertDialogButtonType.CLOSE
                            ),
                            onClose = {
                                handleAuthToken(token)
                            }
                        )
                    )
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

    private fun fetchAuthJsonFromServer(context: Context): String {
        // fetch authentication mock json
        return """
            {
              "challenge": "T1xCsnxM2DNL2KdK5CLa6fMhD7OBqho6syzInk_n-Uo",
              "allowCredentials": [],
              "timeout": 1800000,
              "userVerification": "required",
              "rpId": "credential-manager-app-test.glitch.me"
            }
        """.trimIndent()
    }

    @SuppressLint("PublicKeyCredential")
    fun createPasskey(
        requestJson: String,
        preferImmediatelyAvailableCredentials: Boolean,
        context: Context
    ) {
        val credentialManager = CredentialManager.create(context)
        val createPublicKeyCredentialRequest = CreatePublicKeyCredentialRequest(
            requestJson = requestJson,
            preferImmediatelyAvailableCredentials = preferImmediatelyAvailableCredentials,
        )
        viewModelScope.launch {
            try {
                val result = credentialManager.createCredential(
                    context = context,
                    request = createPublicKeyCredentialRequest,
                )
                handlePasskeyRegistrationResult(result)
            } catch (e : CreateCredentialException){
                handleSignUpFailure(e)
            }
        }
    }

    private fun handlePasskeyRegistrationResult(result: CreateCredentialResponse) {

    }

    private fun handleSignUpFailure(e: CreateCredentialException) {
        when (e) {
            is CreatePublicKeyCredentialDomException -> {
                // Handle the passkey DOM errors thrown according to the
                // WebAuthn spec.
            }
            is CreateCredentialCancellationException -> {
                // The user intentionally canceled the operation and chose not
                // to register the credential.
            }
            is CreateCredentialInterruptedException -> {
                // Retry-able error. Consider retrying the call.
            }
            is CreateCredentialProviderConfigurationException -> {
                // Your app is missing the provider configuration dependency.
                // Most likely, you're missing the
                // "credentials-play-services-auth" module.
            }
            is CreateCredentialUnknownException -> {

            }
            is CreateCredentialCustomException -> {
                // You have encountered an error from a 3rd-party SDK. If you
                // make the API call with a request object that's a subclass of
                // CreateCustomCredentialRequest using a 3rd-party SDK, then you
                // should check for any custom exception type constants within
                // that SDK to match with e.type. Otherwise, drop or log the
                // exception.
            }
            else -> Log.w(TAG, "Unexpected exception type ${e::class.java.name}")
        }
    }


    private suspend fun getSavedCredentials(
        credentialManager: CredentialManager,
        context: Context
    ): String? {

        val getPublicKeyCredentialOption =
            GetPublicKeyCredentialOption(fetchAuthJsonFromServer(context))
        val getPasswordOption = GetPasswordOption()

        // call getCredential() with required credential options
        val result = try {
            credentialManager.getCredential(
                context,
                GetCredentialRequest(
                    listOf(
                        getPasswordOption,
                        getPublicKeyCredentialOption,
                    )
                ),
            )
        } catch (e: Exception) {
            Log.e("Auth", "getCredential failed with exception: " + e.message.toString())
            return null
        }

        if (result.credential is PublicKeyCredential) {
            val cred = result.credential as PublicKeyCredential
            return "Passkey: ${cred.authenticationResponseJson}"
        }

        return null
    }

    private suspend fun saveToken(token:String){
        dataPreferencesStore.write(DataPreferencesStore.TOKEN,token)
    }
    private suspend fun navigateHomePage(){
        navigator.navigate(Destination.HomeGraph) {
            this.popUpTo(0)
        }
    }

    private fun handleAuthToken(res: UserOpResponse) {
        if (!res.error && res.token.isNotBlank()) {
            viewModelScope.launch {
                saveToken(res.token)
                if (_createPubkey.value){
                    registerPasskeyUseCase.execute().collectLatest { registerJson ->
                        val json = Json { prettyPrint = true }.encodeToString(registerJson)
                        createPasskey(
                            json,
                            false,
                            context
                        )
                        navigateHomePage()
                    }
                }else{
                    navigateHomePage()
                }
            }
        }
        //TODO if fails, show popup
    }
}