package com.dag.nexq_app.presentation.userop.domain.usecase

import com.dag.nexq_app.presentation.userop.domain.model.RegisterRequest
import com.dag.nexq_app.presentation.userop.domain.model.UserOpResponse
import com.dag.nexq_app.presentation.userop.domain.repository.IUserOpService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val userOpService: IUserOpService) {

    fun execute(registerRequest: RegisterRequest): Flow<UserOpResponse> =
        userOpService.register(registerRequest)
}