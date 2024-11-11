package com.dag.nexq_app.presentation.quiz.create.presentation

import com.dag.nexq_app.base.BaseVS

sealed class CreateQuizVS: BaseVS {
    data object Default: CreateQuizVS()
    data class QuizCreated(val id:String): CreateQuizVS()
}