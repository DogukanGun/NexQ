package com.dag.nexq_app.presentation.quiz.createwithpdf.domain.repository

import com.dag.nexq_app.presentation.quiz.createwithpdf.domain.model.GenerateQuizResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface ICreateQuizWithPDFService {
    fun generateQuiz(
        file: MultipartBody.Part
    ): Flow<GenerateQuizResponse>
}