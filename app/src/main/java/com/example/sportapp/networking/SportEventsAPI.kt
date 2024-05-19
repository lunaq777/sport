package com.example.sportapp.networking

import retrofit2.Response
import retrofit2.http.GET

interface SportEventsAPI {

    @GET("api/sports")
    suspend fun getEvents(): Response<List<SportResponse>>
}