package com.android.academy.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl(MoviesService.BASE_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val moviesClient: MoviesService = retrofit.create(MoviesService::class.java)

}