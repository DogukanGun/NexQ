package com.dag.nexq_app.presentation.quiz.createwithpdf.domain.usecase

import com.dag.nexq_app.presentation.quiz.createwithpdf.domain.model.GenerateQuizResponse
import com.dag.nexq_app.presentation.quiz.createwithpdf.domain.repository.ICreateQuizWithPDFService
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class GenerateQuizUseCase @Inject constructor(
    private val createQuizWithPDFService: ICreateQuizWithPDFService
){
    fun execute(file:MultipartBody.Part):Flow<GenerateQuizResponse> =
        createQuizWithPDFService.generateQuiz(file)
}