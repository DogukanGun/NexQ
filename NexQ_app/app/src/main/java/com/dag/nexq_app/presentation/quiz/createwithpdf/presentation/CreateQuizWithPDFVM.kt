package com.dag.nexq_app.presentation.quiz.createwithpdf.presentation

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.viewModelScope
import com.dag.nexq_app.R
import com.dag.nexq_app.base.AlertDialogManager
import com.dag.nexq_app.base.BaseVM
import com.dag.nexq_app.base.navigation.DefaultNavigator
import com.dag.nexq_app.data.AlertDialogButton
import com.dag.nexq_app.data.AlertDialogButtonType
import com.dag.nexq_app.data.AlertDialogModel
import com.dag.nexq_app.presentation.quiz.createwithpdf.domain.usecase.GenerateQuizUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class CreateQuizWithPDFVM @Inject constructor(
    private val defaultNavigator: DefaultNavigator,
    private val alertDialogManager: AlertDialogManager,
    private val generateQuizUseCase: GenerateQuizUseCase
): BaseVM<CreateQuizWithPDFVS>(CreateQuizWithPDFVS.Default) {

    fun goBack(){
        viewModelScope.launch {
            defaultNavigator.navigateUp()
        }
    }

    fun getFileName(context: Context, uri: Uri): String? {
        var name: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor? = context.contentResolver.query(
                uri,
                arrayOf(OpenableColumns.DISPLAY_NAME),
                null,
                null,
                null
            )
            cursor?.use {
                if (it.moveToFirst()) {
                    name = it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
        if (name == null) {
            name = uri.path?.substringAfterLast('/')
        }
        return name
    }

    fun createQuiz(context: Context, fileUri: Uri){
        val contentResolver = context.contentResolver

        val inputStream = contentResolver.openInputStream(fileUri) ?: throw IllegalStateException("Cannot open file")
        val fileBytes = inputStream.readBytes()
        val fileName = getFileName(context, fileUri) // Use the previously defined `getFileName` function
        inputStream.close()
        val requestBody = fileBytes
            .toRequestBody(
                "application/pdf".toMediaTypeOrNull(),
                0, fileBytes.size
            )
        val filePart = MultipartBody.Part.createFormData("file", fileName, requestBody)
        viewModelScope.launch {
            generateQuizUseCase.execute(filePart).collect{
                _viewState.value = CreateQuizWithPDFVS.QuizCreated(it)
                alertDialogManager.showAlert(
                    AlertDialogModel(
                        title = context.getString(R.string.add_quiz_by_pdf_dialog_box_title),
                        message = context.getString(R.string.add_quiz_by_pdf_dialog_box_text),
                        positiveButton = AlertDialogButton(
                            text = context.getString(R.string.add_quiz_by_pdf_dialog_box_positive_button_text),
                            onClick = {

                            },
                            type = AlertDialogButtonType.CUSTOM
                        ),
                        negativeButton = AlertDialogButton(
                            text = context.getString(R.string.add_quiz_by_pdf_dialog_box_negative_button_text),
                            onClick = {},
                            type = AlertDialogButtonType.CUSTOM
                        )
                    )
                )
            }
        }
    }
}