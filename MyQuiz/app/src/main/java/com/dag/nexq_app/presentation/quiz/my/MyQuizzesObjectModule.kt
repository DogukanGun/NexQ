package com.dag.nexq_app.presentation.quiz.my

import com.dag.nexq_app.presentation.quiz.my.data.repository.MyQuizzesServiceImpl
import com.dag.nexq_app.presentation.quiz.my.domain.repository.IMyQuizzesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class MyQuizzesObjectModule {
    @Provides
    fun provideMyQuizzesService(@Named("Authorized") retrofit: Retrofit): IMyQuizzesService {
        return MyQuizzesServiceImpl(retrofit)
    }
}