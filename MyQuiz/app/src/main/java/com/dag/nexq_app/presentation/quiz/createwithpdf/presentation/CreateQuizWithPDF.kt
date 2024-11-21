package com.dag.nexq_app.presentation.quiz.createwithpdf.presentation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dag.nexq_app.R
import com.dag.nexq_app.base.components.BackNavigationBar
import com.dag.nexq_app.base.components.CustomButton

@Composable
fun CreateQuizWithPDF(
    createQuizWithPDFVM: CreateQuizWithPDFVM = hiltViewModel()
) {
    val context = LocalContext.current
    var pdfUri by remember { mutableStateOf<Uri?>(null) }
    var pdfFileName by remember {
        mutableStateOf("")
    }
    val launcherPDF = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        pdfUri = uri
        uri?.let {
            createQuizWithPDFVM.getFileName(context, it)?.let { name -> pdfFileName = name }
        }
    }
    BackNavigationBar(backButtonClick = {
        createQuizWithPDFVM.goBack()
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text =
                stringResource(id = R.string.add_quiz_by_pdf_name_tag)
                    .plus(" ")
                    .plus(pdfFileName),
                style = MaterialTheme.typography.headlineMedium
            )
            Column {
                CustomButton(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = if (pdfUri == null) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.secondary,
                    text = if (pdfUri == null)
                        stringResource(id = R.string.add_quiz_by_pdf_button_text)
                    else stringResource(id = R.string.add_quiz_by_pdf_button_secondary_text)
                ) {
                    launcherPDF.launch("application/pdf")
                }
                AnimatedVisibility(visible = pdfUri != null) {
                    CustomButton(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        text = stringResource(id = R.string.add_quiz_by_pdf_upload_button_text)
                    ) {
                        createQuizWithPDFVM.createQuiz(context,pdfUri!!)
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun CreateQuizWithPDFPreview() {
    CreateQuizWithPDF()
}