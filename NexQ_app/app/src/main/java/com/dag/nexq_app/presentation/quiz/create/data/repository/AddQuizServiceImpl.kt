package com.dag.nexq_app.presentation.quiz.create.data.repository

import com.dag.nexq_app.presentation.quiz.create.data.dataSource.AddQuizService
import com.dag.nexq_app.presentation.quiz.create.domain.model.AddQuizRequest
import com.dag.nexq_app.presentation.quiz.create.domain.model.AddQuizResponse
import com.dag.nexq_app.presentation.quiz.create.domain.repository.IAddQuizService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import javax.inject.Inject

class AddQuizServiceImpl constructor(retrofit: Retrofit) : IAddQuizService {

    private val addQuizService = retrofit.create(AddQuizService::class.java)

    override fun addQuiz(addQuizRequest: AddQuizRequest): Flow<AddQuizResponse> = flow {
        emit(addQuizService.addQuiz(addQuizRequest))
    }
}