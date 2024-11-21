package com.dag.nexq_app.di

import android.content.Context
import com.dag.nexq_app.BuildConfig
import com.dag.nexq_app.base.AlertDialogManager
import com.dag.nexq_app.base.navigation.DefaultNavigator
import com.dag.nexq_app.base.navigation.Destination
import com.dag.nexq_app.base.network.Authenticator
import com.dag.nexq_app.base.network.HttpLogger
import com.dag.nexq_app.domain.DataPreferencesStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ObjectModules {

    @Provides
    @Singleton
    fun provideDataPreferencesStore(
        @ApplicationContext context: Context
    ): DataPreferencesStore {
        return DataPreferencesStore(context)
    }

    @Provides
    @Singleton
    fun provideDefaultNavigator(): DefaultNavigator {
        return DefaultNavigator(startDestination = Destination.LoginScreen)
    }

    @Provides
    @Singleton
    @Named("NotAuthorized")
    fun provideHttpClient(
        httpLogger: HttpLogger
    ): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
        ).addInterceptor(httpLogger).connectTimeout(10000L, TimeUnit.MILLISECONDS)
            .readTimeout(10000L, TimeUnit.MILLISECONDS).writeTimeout(10000L, TimeUnit.MILLISECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named("Authorized")
    fun provideAuthHttpClient(
        httpLogger: HttpLogger,
        authenticator: Authenticator
    ): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
        ).addInterceptor(authenticator).addInterceptor(httpLogger)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES).writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    @Singleton
    @Named("NotAuthorized")
    fun provideRetrofit(
        @Named("NotAuthorized") httpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(httpClient).build()
    }

    @Provides
    @Singleton
    @Named("Authorized")
    fun provideAuthRetrofit(
        @Named("Authorized") httpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(httpClient).build()
    }

    @Provides
    @Singleton
    @Named("Block")
    fun provideRetrofitForBlock(
        @Named("NotAuthorized") httpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BLOCK_API)
            .addConverterFactory(GsonConverterFactory.create()).client(httpClient).build()
    }

    @Provides
    @Singleton
    @Named("Google")
    fun provideRetrofitForGoogle(
        @Named("NotAuthorized") httpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.GOOGLE_API)
            .addConverterFactory(GsonConverterFactory.create()).client(httpClient).build()
    }

    @Provides
    @Singleton
    fun provideAlertDialogManager(): AlertDialogManager {
        return AlertDialogManager()
    }
}