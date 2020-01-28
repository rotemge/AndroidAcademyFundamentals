package com.android.academy.networking

import com.android.academy.MovieModel
import com.google.gson.annotations.SerializedName

data class MoviesListResult(
    @SerializedName("page") val page: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val results: List<MovieResult>
)

data class MovieResult(
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("title") val title: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val releaseDate: String
)

fun MovieResult.toMovieModel(): MovieModel {
    val baseUrl = "https://image.tmdb.org/t/p/"
    val fileSize = "w500"
    return MovieModel(
        id,
        title,
        baseUrl + fileSize + posterPath,
        overview,
        baseUrl + fileSize + backdropPath,
        releaseDate
    )
}