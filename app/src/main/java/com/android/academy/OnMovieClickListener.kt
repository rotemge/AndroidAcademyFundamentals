package com.android.academy

import com.android.academy.model.MovieModel

interface OnMovieClickListener {
    fun onMovieClicked(movie: MovieModel)
}