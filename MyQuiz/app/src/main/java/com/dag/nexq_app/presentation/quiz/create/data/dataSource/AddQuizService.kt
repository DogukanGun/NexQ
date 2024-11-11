package com.dag.nexq_app.presentation.quiz.create.data.dataSource

import com.dag.nexq_app.presentation.quiz.create.domain.model.AddQuizRequest
import com.dag.nexq_app.presentation.quiz.create.domain.model.AddQuizResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AddQuizService {

    @POST("/quiz/add")
    suspend fun addQuiz(@Body addQuizRequest: AddQuizRequest): AddQuizResponse
}