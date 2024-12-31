package com.dag.nexq_app.presentation.quiz.createwithpdf.data.datasource

import com.dag.nexq_app.presentation.quiz.createwithpdf.domain.model.GenerateQuizResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CreateQuizWithPDFService {
    @Multipart
    @POST("/quiz/generate")
    suspend fun generateQuiz(
        @Part file: MultipartBody.Part
    ):GenerateQuizResponse
}