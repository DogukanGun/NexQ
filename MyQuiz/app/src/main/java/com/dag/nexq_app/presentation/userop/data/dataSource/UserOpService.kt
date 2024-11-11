package com.dag.nexq_app.presentation.userop.data.dataSource

import com.dag.nexq_app.presentation.userop.domain.model.LoginRequest
import com.dag.nexq_app.presentation.userop.domain.model.RegisterRequest
import com.dag.nexq_app.presentation.userop.domain.model.UserOpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserOpService {

    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): UserOpResponse

    @POST("/auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): UserOpResponse

}