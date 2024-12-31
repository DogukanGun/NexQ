package com.dag.nexq_app.presentation.userop.domain.usecase

import com.dag.nexq_app.presentation.userop.domain.model.RegisterPasskeyResponse
import com.dag.nexq_app.presentation.userop.domain.repository.IUserOpService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterPasskeyUseCase @Inject constructor(private val userOpService: IUserOpService) {
    fun execute():Flow<RegisterPasskeyResponse> = userOpService.registerPasskey()
}