package com.dag.nexq_app.presentation.quiz.createwithpdf.data.repository

import com.dag.nexq_app.presentation.quiz.createwithpdf.data.datasource.CreateQuizWithPDFService
import com.dag.nexq_app.presentation.quiz.createwithpdf.domain.model.GenerateQuizResponse
import com.dag.nexq_app.presentation.quiz.createwithpdf.domain.repository.ICreateQuizWithPDFService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.Retrofit


class CreateQuizWithPDFServiceImpl constructor(retrofit: Retrofit): ICreateQuizWithPDFService {
    private val service = retrofit.create(CreateQuizWithPDFService::class.java)

    override fun generateQuiz(file: MultipartBody.Part): Flow<GenerateQuizResponse> = flow{
        service.generateQuiz(file)
    }
}