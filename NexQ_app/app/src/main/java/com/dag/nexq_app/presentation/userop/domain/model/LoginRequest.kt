package com.dag.nexq_app.presentation.userop.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username:String,
    val password:String
)
