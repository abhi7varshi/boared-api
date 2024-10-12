package com.example.boaredapi.data.di

import com.example.boaredapi.data.local.PostDao
import com.example.boaredapi.data.remote.JsonPlaceHolderApi
import com.example.boaredapi.data.repository.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    @Singleton
    @Provides
    fun provideRetrofit(): JsonPlaceHolderApi {
        val networkJson = Json { ignoreUnknownKeys = true }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .callFactory( //Logging
                OkHttpClient.Builder()
                    .addNetworkInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .build()
            )
            .addConverterFactory(
                //kotlin serialization
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(JsonPlaceHolderApi::class.java)
    }

    @Singleton
    @Provides
    fun providesPostRepository(
        api: JsonPlaceHolderApi,
        postDao: PostDao
    ): PostRepository {
        return PostRepository(api, postDao)
    }
}