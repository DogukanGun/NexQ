package com.dag.nexq_app.presentation.userop.domain.usecase

import com.dag.nexq_app.presentation.userop.domain.model.LoginRequest
import com.dag.nexq_app.presentation.userop.domain.model.UserOpResponse
import com.dag.nexq_app.presentation.userop.domain.repository.IUserOpService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val userService: IUserOpService) {

    fun execute(loginRequest: LoginRequest): Flow<UserOpResponse> =
        userService.login(loginRequest)

}