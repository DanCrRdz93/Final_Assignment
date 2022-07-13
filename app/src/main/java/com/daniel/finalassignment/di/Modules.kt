package com.daniel.finalassignment.di

import com.daniel.finalassignment.network.BreakingBadApi
import com.daniel.finalassignment.network.BreakingBadRepository
import com.daniel.finalassignment.network.BreakingBadRepositoryImpl
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesGson(): Gson = Gson()

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(BreakingBadApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

    @Provides
    fun providesBreakingBadApi(retrofit: Retrofit): BreakingBadApi =
        retrofit.create(BreakingBadApi::class.java)

    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

}

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule{

    @Binds
    abstract fun bindsBreakingBadRepository(
        breakingBadRepositoryImpl: BreakingBadRepositoryImpl
    ): BreakingBadRepository

}

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

}


