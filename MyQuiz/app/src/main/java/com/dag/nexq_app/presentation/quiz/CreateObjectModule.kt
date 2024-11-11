package com.dag.nexq_app.presentation.quiz

import com.dag.nexq_app.presentation.quiz.create.data.dataSource.AddQuizService
import com.dag.nexq_app.presentation.quiz.create.data.repository.AddQuizServiceImpl
import com.dag.nexq_app.presentation.quiz.create.domain.repository.IAddQuizService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class CreateObjectModule {

    @Provides
    fun provideAddQuizService(@Named("Authorized") retrofit: Retrofit): IAddQuizService {
        return AddQuizServiceImpl(retrofit)
    }
}