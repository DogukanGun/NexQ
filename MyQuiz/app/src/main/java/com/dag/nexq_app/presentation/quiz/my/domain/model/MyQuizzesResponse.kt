package com.dag.nexq_app.presentation.quiz.my.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MyQuizzesResponse(
    val name: String?,
    val id: String,
)
