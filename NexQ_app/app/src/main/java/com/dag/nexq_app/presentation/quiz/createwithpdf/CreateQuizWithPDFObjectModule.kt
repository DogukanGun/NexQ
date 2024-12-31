package com.dag.nexq_app.presentation.quiz.createwithpdf

import com.dag.nexq_app.presentation.quiz.createwithpdf.data.repository.CreateQuizWithPDFServiceImpl
import com.dag.nexq_app.presentation.quiz.createwithpdf.domain.repository.ICreateQuizWithPDFService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class CreateQuizWithPDFObjectModule {
    @Provides
    fun provideCreateQuizWithPDFService(@Named("Authorized") retrofit: Retrofit): ICreateQuizWithPDFService {
        return CreateQuizWithPDFServiceImpl(retrofit)
    }
}