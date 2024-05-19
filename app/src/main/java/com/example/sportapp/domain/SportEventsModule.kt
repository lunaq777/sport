package com.example.sportapp.domain

import com.example.sportapp.networking.SportEventsAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://618d3aa7fe09aa001744060a.mockapi.io/"
@Module
@InstallIn(SingletonComponent::class)
class SportEventsModule {

    @Provides
    @Singleton
    fun theRetrofitInstance() : SportEventsAPI {
        val api : SportEventsAPI by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SportEventsAPI::class.java)
        }
        return api
    }
}