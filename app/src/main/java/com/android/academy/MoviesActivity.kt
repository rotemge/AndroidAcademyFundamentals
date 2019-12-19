package com.android.academy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : AppCompatActivity() {

    private val movies: MutableList<MovieModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        loadMovies()
        initRecyclerView()
    }

    private fun loadMovies() {
        movies.add(
            MovieModel(
                getString(R.string.jurassic_world_title),
                R.drawable.jurassic_world_poster,
                getString(R.string.jurassic_world_overview)
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.the_meg_title),
                R.drawable.the_meg_poster,
                getString(R.string.the_meg_overview)
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.black_panther_title),
                R.drawable.black_panther_poster,
                getString(R.string.black_panther_overview)
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.deadpool_title),
                R.drawable.deadpool_2_poster,
                getString(R.string.deadpool_overview)
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.guardians_galaxy_title),
                R.drawable.guardians_galaxy_poster,
                getString(R.string.guardians_galaxy_overview)
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.avengers_title),
                R.drawable.avengers_poster,
                getString(R.string.avengers_overview)
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.interstellar_title),
                R.drawable.interstellar_poster,
                getString(R.string.interstellar_overview)
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.jurassic_world_title),
                R.drawable.jurassic_world_poster,
                getString(R.string.jurassic_world_overview)
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.oceans_8_title),
                R.drawable.oceans_8_poster,
                getString(R.string.oceans_8_overview)
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.the_first_purge_title),
                R.drawable.the_first_purge_poster,
                getString(R.string.the_first_purge_overview)
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.thor_ragnarok_title),
                R.drawable.thor_ragnarok_poster,
                getString(R.string.thor_ragnarok_overview)
            )
        )
    }

    private fun initRecyclerView() {
        rvMoviesList.layoutManager = LinearLayoutManager(this)
        val moviesAdapter = MoviesViewAdapter(this)
        rvMoviesList.adapter = moviesAdapter
        moviesAdapter.setData(movies)
    }

}
