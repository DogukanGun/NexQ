package com.dag.nexq_app.presentation.quiz.create.domain.usecase

import com.dag.nexq_app.presentation.quiz.create.domain.model.AddQuizRequest
import com.dag.nexq_app.presentation.quiz.create.domain.model.AddQuizResponse
import com.dag.nexq_app.presentation.quiz.create.domain.repository.IAddQuizService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddQuizUseCase @Inject constructor(private val quizService: IAddQuizService) {

    fun execute(addQuizRequest: AddQuizRequest): Flow<AddQuizResponse> =
        quizService.addQuiz(addQuizRequest)

}