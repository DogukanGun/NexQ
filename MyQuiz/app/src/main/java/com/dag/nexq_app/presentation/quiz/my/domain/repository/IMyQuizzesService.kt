package com.dag.nexq_app.presentation.quiz.my.domain.repository

import com.dag.nexq_app.presentation.quiz.my.domain.model.MyQuizzesResponse
import kotlinx.coroutines.flow.Flow

interface IMyQuizzesService {
    fun getMyQuizzes(): Flow<List<MyQuizzesResponse>>
}