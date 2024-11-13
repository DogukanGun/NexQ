package com.dag.nexq_app.presentation.quiz.my.data.repository

import com.dag.nexq_app.presentation.quiz.my.data.dataSource.MyQuizzesService
import com.dag.nexq_app.presentation.quiz.my.domain.model.MyQuizzesResponse
import com.dag.nexq_app.presentation.quiz.my.domain.repository.IMyQuizzesService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit

class MyQuizzesServiceImpl constructor(retrofit: Retrofit): IMyQuizzesService {

    private val service = retrofit.create(MyQuizzesService::class.java)

    override fun getMyQuizzes(): Flow<List<MyQuizzesResponse>> = flow{
        emit(service.getMyQuizzes())
    }
}