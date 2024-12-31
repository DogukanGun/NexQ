package com.dag.nexq_app.presentation.userop.domain.repository

import com.dag.nexq_app.presentation.userop.domain.model.LoginRequest
import com.dag.nexq_app.presentation.userop.domain.model.RegisterPasskeyResponse
import com.dag.nexq_app.presentation.userop.domain.model.RegisterRequest
import com.dag.nexq_app.presentation.userop.domain.model.UserOpResponse
import kotlinx.coroutines.flow.Flow

interface IUserOpService {

    fun login(loginRequest: LoginRequest): Flow<UserOpResponse>

    fun register(registerRequest: RegisterRequest): Flow<UserOpResponse>

    fun registerPasskey(): Flow<RegisterPasskeyResponse>

}