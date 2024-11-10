package com.dag.nexq_app.presentation.quiz.create.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dag.nexq_app.presentation.quiz.components.Accordion
import com.dag.nexq_app.presentation.quiz.components.QuizBody
import com.dag.nexq_app.presentation.quiz.components.QuizOption
import com.dag.nexq_app.presentation.quiz.create.domain.model.Answer
import com.dag.nexq_app.presentation.quiz.create.domain.model.QuizModel


@Composable
fun CreateQuizRow(
    index:Int,
    editMode: Boolean = true,
    quizModel: MutableState<QuizModel> // Passing MutableState for inout behavior
){
    val answers = remember {
        mutableListOf<String>()
    }
    Accordion(
        title = "Question ".plus(index),
        modifier = Modifier.fillMaxWidth()
    ) {
        QuizBody(
            title = quizModel.value.question,
            editMode = editMode,
            enabled = answers.map { it.count() }.any { it == 0 },
            onClick = {}
        ){
            quizModel.value.answers.forEachIndexed { quizIndex, answer ->
                QuizOption(
                    index = quizIndex.plus(1),
                    editMode = editMode,
                    option = quizModel.value.answers[quizIndex].answer,
                    onTextChange = {
                        answers[quizIndex] = it
                    }
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}

@Preview
@Composable
fun CreateQuizRowPreview(){
    CreateQuizRow(
        index = 1,
        quizModel = mutableStateOf(QuizModel(
            "This is a test question",
            listOf(
                Answer("This is wrong answer",false),
                Answer("This is wrong answer",false),
                Answer("This is wrong answer",false),
                Answer("This is correct answer",true)
            )
        ))
    )
}