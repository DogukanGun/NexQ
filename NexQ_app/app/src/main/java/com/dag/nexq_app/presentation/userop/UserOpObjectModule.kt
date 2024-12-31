package com.dag.nexq_app.presentation.userop

import com.dag.nexq_app.presentation.userop.data.repository.UserOpServiceImpl
import com.dag.nexq_app.presentation.userop.domain.repository.IUserOpService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
class UserOpObjectModule {
    @Provides
    fun provideUserService(
        @Named("NotAuthorized") retrofit: Retrofit,
        @Named("Authorized") authRetrofit: Retrofit
    ):IUserOpService{
        return UserOpServiceImpl(retrofit,authRetrofit)
    }
}