package com.dag.nexq_app.presentation.userop.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val username:String,
    val password:String,
    val email:String
)