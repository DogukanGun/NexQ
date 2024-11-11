package com.dag.nexq_app.presentation.quiz.create.domain.repository

import com.dag.nexq_app.presentation.quiz.create.domain.model.AddQuizRequest
import com.dag.nexq_app.presentation.quiz.create.domain.model.AddQuizResponse
import kotlinx.coroutines.flow.Flow

interface IAddQuizService {
    fun addQuiz(addQuizRequest: AddQuizRequest): Flow<AddQuizResponse>
}