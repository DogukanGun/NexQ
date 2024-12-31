package com.dag.nexq_app.presentation.quiz.create.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AddQuizResponse(
    val status:Boolean,
    val id:String
)
