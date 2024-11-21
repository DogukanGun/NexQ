package com.dag.nexq_app.presentation.quiz.createwithpdf.presentation

import com.dag.nexq_app.base.BaseVS
import com.dag.nexq_app.presentation.quiz.createwithpdf.domain.model.GenerateQuizResponse

sealed class CreateQuizWithPDFVS: BaseVS {
    data object Default: CreateQuizWithPDFVS()
    data class QuizCreated(val response:GenerateQuizResponse):CreateQuizWithPDFVS()
}