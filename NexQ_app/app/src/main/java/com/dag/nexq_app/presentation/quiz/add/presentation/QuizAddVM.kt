package com.dag.nexq_app.presentation.quiz.add.presentation

import androidx.lifecycle.viewModelScope
import com.dag.nexq_app.base.BaseVM
import com.dag.nexq_app.base.navigation.DefaultNavigator
import com.dag.nexq_app.presentation.quiz.create.domain.usecase.AddQuizUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizAddVM @Inject constructor(
    private val defaultNavigator: DefaultNavigator
): BaseVM<QuizAddVS>(QuizAddVS.Default) {

    fun goBack() {
        viewModelScope.launch {
            defaultNavigator.navigateUp()
        }
    }

}