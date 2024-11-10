package com.dag.nexq_app.presentation.quiz.create.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dag.nexq_app.presentation.quiz.create.domain.model.QuizModel

@Composable
fun CreateQuiz(
    createQuizVM: CreateQuizVM = hiltViewModel(),
    navController: NavController
){
    val quizzes = remember {
        List(10) { mutableStateOf(QuizModel.createEditMode()) }
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ){
        items(10){
            CreateQuizRow(index = it.plus(1), quizModel = quizzes[it])
        }
    }
}

@Composable
@Preview
fun CreateQuizPreview(){
    CreateQuiz(
        navController = rememberNavController()
    )
}
