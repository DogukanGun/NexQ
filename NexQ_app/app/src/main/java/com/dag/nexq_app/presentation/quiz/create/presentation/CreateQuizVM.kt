package com.dag.nexq_app.presentation.quiz.create.presentation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.dag.nexq_app.base.AlertDialogManager
import com.dag.nexq_app.base.BaseVM
import com.dag.nexq_app.base.navigation.DefaultNavigator
import com.dag.nexq_app.base.navigation.Destination
import com.dag.nexq_app.data.AlertDialogButton
import com.dag.nexq_app.data.AlertDialogButtonType
import com.dag.nexq_app.data.AlertDialogModel
import com.dag.nexq_app.presentation.quiz.create.domain.model.QuizModel
import com.dag.nexq_app.presentation.quiz.create.domain.model.convertToAddQuizRequest
import com.dag.nexq_app.presentation.quiz.create.domain.usecase.AddQuizUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateQuizVM @Inject constructor(
    private val addQuizUseCase: AddQuizUseCase,
    private val alertDialogManager: AlertDialogManager,
    private val defaultNavigator: DefaultNavigator
) : BaseVM<CreateQuizVS>(CreateQuizVS.Default) {

    fun goBack(){
        viewModelScope.launch {
            defaultNavigator.navigateUp()
        }
    }

    fun addQuiz(context: Context, quizName: String, list: List<QuizModel>) {
        val requestBody = convertToAddQuizRequest(quizName, list)
        viewModelScope.launch {
            addQuizUseCase.execute(requestBody).collectLatest {
                alertDialogManager.showAlert(
                    AlertDialogModel(
                        "Yuupiii!!!",
                        "Quiz is created. The id is ${it.id} .",
                        positiveButton=AlertDialogButton(
                            "Save ID",
                            onClick = {
                                copyToClipboard(context, it.id)
                                navigateHome()
                            },
                            type = AlertDialogButtonType.CUSTOM
                        ),
                        negativeButton = AlertDialogButton(
                            "Okay",
                            onClick = {
                                navigateHome()
                            },
                            type = AlertDialogButtonType.CUSTOM
                        )
                    )
                )
            }
        }
    }

    private fun navigateHome() {
        viewModelScope.launch {
            defaultNavigator.navigate(Destination.HomeScreen)
        }
    }

    private fun copyToClipboard(context: Context, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "ID copied to clipboard", Toast.LENGTH_SHORT).show()
    }
}