package com.dag.nexq_app.presentation.quiz.my.domain.usecase

import com.dag.nexq_app.presentation.quiz.my.domain.model.MyQuizzesResponse
import com.dag.nexq_app.presentation.quiz.my.domain.repository.IMyQuizzesService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyQuizzesUseCase @Inject constructor(private val myQuizzesService: IMyQuizzesService) {

    fun execute():Flow<List<MyQuizzesResponse>> = myQuizzesService.getMyQuizzes()
}