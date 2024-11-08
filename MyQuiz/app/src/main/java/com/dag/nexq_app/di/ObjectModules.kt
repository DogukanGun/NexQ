package com.dag.nexq_app.di

import android.content.Context
import com.dag.nexq_app.BuildConfig
import com.dag.nexq_app.base.navigation.DefaultNavigator
import com.dag.nexq_app.base.navigation.Destination
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
    ):DataPreferencesStore{
        return DataPreferencesStore(context)
    }

    @Provides
    @Singleton
    fun provideDefaultNavigator():DefaultNavigator{
        return DefaultNavigator(startDestination = Destination.LoginScreen)
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .connectTimeout(10000L, TimeUnit.MILLISECONDS)
            .readTimeout(10000L, TimeUnit.MILLISECONDS)
            .writeTimeout(10000L, TimeUnit.MILLISECONDS).build()
    }

    @Provides
    @Singleton
    @Named("NotAuthorized")
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://"+BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }
}