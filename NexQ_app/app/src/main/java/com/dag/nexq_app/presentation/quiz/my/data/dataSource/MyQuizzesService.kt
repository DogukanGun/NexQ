package com.dag.nexq_app.presentation.quiz.my.data.dataSource

import com.dag.nexq_app.presentation.quiz.my.domain.model.MyQuizzesResponse
import retrofit2.http.GET

interface MyQuizzesService {

    @GET("/quiz/my")
    suspend fun getMyQuizzes():List<MyQuizzesResponse>
}