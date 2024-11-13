package com.dag.nexq_app.presentation.quiz.my.presentation

import com.dag.nexq_app.base.BaseVS

sealed class MyQuizzesVS: BaseVS {
    data object Default: MyQuizzesVS()
}