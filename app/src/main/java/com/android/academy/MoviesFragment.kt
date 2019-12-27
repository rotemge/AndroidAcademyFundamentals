package com.android.academy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment(), OnMovieClickListener {

    private val movies: MutableList<MovieModel> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_movies, container, false)
        loadMovies()
        initRecyclerView()
        return view
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
        context?.let {
            rvMoviesList.layoutManager = LinearLayoutManager(it)
            val moviesAdapter = MoviesViewAdapter(it, this)
            rvMoviesList.adapter = moviesAdapter
            moviesAdapter.setData(movies)
        }
    }

    override fun onMovieClicked(movie: MovieModel) {
        context?.let {
            Toast.makeText(it, movie.name, Toast.LENGTH_SHORT).show()
        }
    }
}
