package com.dag.nexq_app.presentation.userop.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserOpResponse(
    val error:Boolean,
    val token:String
)
