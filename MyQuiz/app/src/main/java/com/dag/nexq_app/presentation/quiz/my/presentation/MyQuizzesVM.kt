package com.dag.nexq_app.presentation.quiz.my.presentation

import androidx.lifecycle.viewModelScope
import com.dag.nexq_app.base.BaseVM
import com.dag.nexq_app.presentation.quiz.my.domain.model.MyQuizzesResponse
import com.dag.nexq_app.presentation.quiz.my.domain.usecase.GetMyQuizzesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MyQuizzesVM @Inject constructor(
    private val getMyQuizzesUseCase: GetMyQuizzesUseCase
): BaseVM<MyQuizzesVS>(MyQuizzesVS.Default) {

    fun getMyQuizzes():Flow<List<MyQuizzesResponse>> = getMyQuizzesUseCase.execute()
}