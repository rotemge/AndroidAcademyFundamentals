package com.android.academy.networking

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesService {

    companion object {
        const val BASE_API_URL = "https://api.themoviedb.org/3/"
        private const val apiKey = ""
        private const val keyQuery = "?api_key=$apiKey"
    }

    @GET("movie/popular$keyQuery")
    fun loadPopularMovies(): Call<MoviesListResult>

    @GET("movie/{movie_id}/videos$keyQuery&language=en-US")
    fun loadMovieTrailer(@Path("movie_id") movieId: Int): Call<VideosListResult>

}