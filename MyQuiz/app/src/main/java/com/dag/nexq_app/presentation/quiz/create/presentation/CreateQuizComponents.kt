package com.dag.nexq_app.presentation.quiz.create.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dag.nexq_app.R
import com.dag.nexq_app.base.components.Visibility
import com.dag.nexq_app.presentation.quiz.components.Accordion
import com.dag.nexq_app.presentation.quiz.components.QuizBody
import com.dag.nexq_app.presentation.quiz.components.QuizEditTextField
import com.dag.nexq_app.presentation.quiz.components.QuizOption
import com.dag.nexq_app.presentation.quiz.create.domain.model.Answer
import com.dag.nexq_app.presentation.quiz.create.domain.model.QuizModel


@Composable
fun CreateQuizRow(
    index: Int,
    editMode: Boolean = true,
    quizModel: MutableState<QuizModel> // Passing MutableState for inout behavior
) {
    val enabled = remember(quizModel.value.answers) {
        quizModel.value.answers.isNotEmpty() && quizModel.value.answers.all { it.answer.isNotBlank() }
    }

    Accordion(
        title = "Question ".plus(index),
        modifier = Modifier.fillMaxWidth()
    ) {
        QuizBody(
            title = quizModel.value.question,
            editMode = editMode,
            enabled = enabled,
            buttonText = "Save",
            onClick = {}
        ) {
            quizModel.value.answers.forEachIndexed { quizIndex, answer ->
                Spacer(modifier = Modifier.size(8.dp))
                QuizOption(
                    index = quizIndex.plus(1),
                    editMode = editMode,
                    option = quizModel.value.answers[quizIndex].answer,
                    onTextChange = {
                        val updatedAnswers = quizModel.value.answers.toMutableList().apply {
                            this[quizIndex] = this[quizIndex].copy(answer = it)
                        }
                        quizModel.value = quizModel.value.copy(answers = updatedAnswers)
                    }
                )
            }
        }
    }
}

@Composable
fun ListDropdown(
    title: String,
    dropdownState: MutableState<Boolean> = mutableStateOf(false),
    innerContent: (@Composable () -> Unit)? = null
) {

    Column {
        Row(
            modifier = Modifier
                .padding(vertical = 15.dp)
                .fillMaxWidth()
                .toggleable(
                    value = dropdownState.value,
                    onValueChange = {
                        dropdownState.value = it
                    },
                    role = Role.Button
                )
        ) {
            Text(text = title, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                if (dropdownState.value) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Visibility(case = dropdownState.value) {
            innerContent?.let { it() }
        }
    }


}

@Preview
@Composable
fun CreateQuizRowPreview() {
    CreateQuizRow(
        index = 1,
        quizModel = mutableStateOf(
            QuizModel(
                "This is a test question",
                listOf(
                    Answer("This is wrong answer", false),
                    Answer("This is wrong answer", false),
                    Answer("This is wrong answer", false),
                    Answer("This is correct answer", true)
                )
            )
        )
    )
}