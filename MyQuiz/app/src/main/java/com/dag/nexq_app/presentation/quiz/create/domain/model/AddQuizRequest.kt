package com.dag.nexq_app.presentation.quiz.create.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AddQuizRequest(
    val name:String,
    val questions:List<Question>
)

@Serializable
data class Question(
    val questionBody:String,
    val answers:List<QuestionAnswer>
)

@Serializable
data class QuestionAnswer(
    val content:String,
    val isCorrectAnswer:Boolean
)
