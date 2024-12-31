package com.dag.nexq_app.presentation.home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewModelScope
import com.dag.nexq_app.R
import com.dag.nexq_app.base.AlertDialogManager
import com.dag.nexq_app.base.BaseVM
import com.dag.nexq_app.base.UITextHelper
import com.dag.nexq_app.data.AlertDialogButton
import com.dag.nexq_app.data.AlertDialogButtonType
import com.dag.nexq_app.data.AlertDialogModel
import com.dag.nexq_app.domain.DataPreferencesStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeVM @Inject constructor(
    private val alertDialogManager: AlertDialogManager,
    private val uiTextHelper: UITextHelper,
    private val preferencesStore: DataPreferencesStore
) : BaseVM<HomeVS>(HomeVS.Default) {

    private var quizCode by mutableStateOf("")

    fun joinQuiz() {
        viewModelScope.launch {
            alertDialogManager.showAlert(
                AlertDialogModel(
                    title = uiTextHelper.getString(R.string.solve_quiz_popup_title),
                    message = uiTextHelper.getString(R.string.solve_quiz_popup_input_placeholder),
                    textInput = true,
                    onTextChange = {
                        quizCode = it
                    },
                    positiveButton = AlertDialogButton(
                        text = uiTextHelper.getString(R.string.solve_quiz_popup_button),
                        type = AlertDialogButtonType.CUSTOM,
                        onClick = {
                            navigateToQuizPage()
                        }
                    )
                )
            )
        }
    }

    fun navigateToQuizPage(){

    }

    fun saveWallet(wallet:String){
        viewModelScope.launch {
            preferencesStore.write(DataPreferencesStore.MINING,wallet)
        }
    }

}