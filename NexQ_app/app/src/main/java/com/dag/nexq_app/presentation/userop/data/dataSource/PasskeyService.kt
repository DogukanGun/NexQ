package com.dag.nexq_app.presentation.userop.data.dataSource

import com.dag.nexq_app.presentation.userop.domain.model.RegisterPasskeyResponse
import retrofit2.http.POST

interface PasskeyService {
    @POST("/auth/passkey/create")
    suspend fun registerPasskey(): RegisterPasskeyResponse
}