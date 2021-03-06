package com.android.academy.model

import com.android.academy.networking.MoviesListResult
import com.android.academy.networking.toMovieModel

object MoviesContent {

    private val movies: MutableList<MovieModel> = mutableListOf()

    fun getMovies(): List<MovieModel> = movies.toList()

    fun getCount(): Int = movies.size

    fun getMovie(position: Int) = movies[position]

    fun getIndexOfMovie(movie: MovieModel) = movies.indexOf(movie)

    fun fromList(moviesList: List<MovieModel>) {
        movies.clear()
        movies.addAll(moviesList)
    }

    fun fromResults(moviesResult: MoviesListResult) {
        movies.clear()
        moviesResult.results.forEach {
            movies.add(it.toMovieModel())
        }
    }

}