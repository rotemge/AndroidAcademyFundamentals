package com.android.academy.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(MoviesService.BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val moviesClient: MoviesService by lazy {
        retrofit.create(MoviesService::class.java)
    }

}