package com.dag.nexq_app.presentation.quiz.create.domain.model

data class QuizModel(
    var question:String,
    val answers: List<Answer>,
) {
    companion object {
        fun createEditMode():QuizModel{
            return QuizModel(
                question = "This is the edit question",
                answers = listOf(
                    Answer("",false),
                    Answer("",false),
                    Answer("",false),
                    Answer("",true)
                )
            )
        }
    }
}

data class Answer(
    var answer: String,
    var isCorrectAnswer: Boolean
)

