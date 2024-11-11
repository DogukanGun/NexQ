package com.dag.nexq_app.presentation.quiz.create.domain.model

fun convertToAddQuizRequest(quizName:String,quizModelList: List<QuizModel>): AddQuizRequest {
    return AddQuizRequest(
        name = quizName,
        questions = quizModelList.map {
            Question(
                questionBody = it.question,
                answers = it.answers.map { answers ->
                    QuestionAnswer(
                        content = answers.answer,
                        isCorrectAnswer = answers.isCorrectAnswer
                    )
                }
            )
        }
    )
}


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

