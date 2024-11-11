package com.dag.nexq_app.presentation.quiz.create.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dag.nexq_app.R
import com.dag.nexq_app.base.components.BackNavigationBar
import com.dag.nexq_app.base.components.CustomButton
import com.dag.nexq_app.presentation.quiz.components.QuizEditTextField
import com.dag.nexq_app.presentation.quiz.create.domain.model.QuizModel

@Composable
fun CreateQuiz(
    createQuizVM: CreateQuizVM = hiltViewModel(),
) {
    val quizzes = remember {
        List(10) { mutableStateOf(QuizModel.createEditMode()) }
    }
    var quizName by remember {
        mutableStateOf("")
    }
    val isShowForQuestions = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    BackNavigationBar(backButtonClick = { createQuizVM.goBack() }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                item {
                    ListDropdown(title = "Quiz") {
                        QuizEditTextField(
                            text = quizName,
                            placeholder = stringResource(id = R.string.quiz_add_quiz_name),
                            onTextChange = { fieldText ->
                                quizName = fieldText
                            }
                        )
                    }
                }
                item {
                    ListDropdown(
                        title = "Questions",
                        dropdownState = isShowForQuestions
                    )
                }
                if (isShowForQuestions.value) {
                    items(10) {
                        CreateQuizRow(
                            index = it.plus(1),
                            quizModel = quizzes[it]
                        )
                    }
                }
            }
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.primary,
                text = stringResource(id = R.string.save_button_text)
            )
            {
                createQuizVM.addQuiz(
                    context,
                    quizName,
                    quizzes.map { it.value }
                )
            }
        }
    }

}

@Composable
@Preview
fun CreateQuizPreview() {
    CreateQuiz()
}
