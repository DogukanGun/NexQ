package com.dag.nexq_app.presentation.userop.data.repository

import com.dag.nexq_app.presentation.userop.data.dataSource.UserOpService
import com.dag.nexq_app.presentation.userop.domain.model.LoginRequest
import com.dag.nexq_app.presentation.userop.domain.model.RegisterRequest
import com.dag.nexq_app.presentation.userop.domain.model.UserOpResponse
import com.dag.nexq_app.presentation.userop.domain.repository.IUserOpService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import javax.inject.Inject

class UserOpServiceImpl constructor(retrofit: Retrofit): IUserOpService {

    private val userRetrofit = retrofit.create(UserOpService::class.java)

    override fun login(loginRequest: LoginRequest): Flow<UserOpResponse> = flow {
        emit(userRetrofit.login(loginRequest))
    }

    override fun register(registerRequest: RegisterRequest): Flow<UserOpResponse> = flow {
        emit(userRetrofit.register(registerRequest))
    }
}