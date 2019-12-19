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
                "Jurassic World - Fallen Kingdom",
                R.drawable.jurassic_world_poster,
                "Description text is here...."
            )
        )
        movies.add(
            MovieModel(
                "The Meg",
                R.drawable.the_meg_poster,
                "Description text is here...."
            )
        )
        movies.add(
            MovieModel(
                "Black Panther",
                R.drawable.black_panther_poster,
                "Description text is here...."
            )
        )
        movies.add(
            MovieModel(
                "Deadpool2",
                R.drawable.deadpool_2_poster,
                "Description text is here...."
            )
        )
        movies.add(
            MovieModel(
                "Guardians Of The Galaxy",
                R.drawable.guardians_galaxy_poster,
                "Description text is here...."
            )
        )
        movies.add(
            MovieModel(
                "Avengers - Infinity War",
                R.drawable.avengers_poster,
                "Description text is here...."
            )
        )
        movies.add(
            MovieModel(
                "Interstellar",
                R.drawable.interstellar_poster,
                "Description text is here...."
            )
        )
        movies.add(
            MovieModel(
                "Jurassic World - Fallen Kingdom",
                R.drawable.jurassic_world_poster,
                "Description text is here...."
            )
        )
        movies.add(
            MovieModel(
                "Ocean's 8",
                R.drawable.oceans_8_poster,
                "Description text is here...."
            )
        )
        movies.add(
            MovieModel(
                "The First Purge",
                R.drawable.the_first_purge_poster,
                "Description text is here...."
            )
        )
        movies.add(
            MovieModel(
                "Thor Ragnarok",
                R.drawable.thor_ragnarok_poster,
                "Description text is here...."
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
