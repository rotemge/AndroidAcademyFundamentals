package com.android.academy

object MoviesContent {

    private val movies: MutableList<MovieModel> = mutableListOf()

    fun getMovies(): List<MovieModel> = movies

    fun getCount(): Int = movies.size

    fun getMovie(position: Int) = movies[position]

    fun getIndexOfMovie(movie: MovieModel) = movies.indexOf(movie)

    fun addMovie(movie: MovieModel) {
        movies.add(movie)
    }

}