package com.android.academy

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MoviesFragment : Fragment(), OnMovieClickListener {

    private var listener: OnMovieClickListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_movies, container, false)
        loadMovies()
        initRecyclerView(view.findViewById(R.id.rvMoviesList))
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMovieClickListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun loadMovies() {
        MoviesContent.addMovie(
            MovieModel(
                getString(R.string.jurassic_world_title),
                R.drawable.jurassic_world_poster,
                getString(R.string.jurassic_world_overview)
            )
        )
        MoviesContent.addMovie(
            MovieModel(
                getString(R.string.the_meg_title),
                R.drawable.the_meg_poster,
                getString(R.string.the_meg_overview)
            )
        )
        MoviesContent.addMovie(
            MovieModel(
                getString(R.string.black_panther_title),
                R.drawable.black_panther_poster,
                getString(R.string.black_panther_overview)
            )
        )
        MoviesContent.addMovie(
            MovieModel(
                getString(R.string.deadpool_title),
                R.drawable.deadpool_2_poster,
                getString(R.string.deadpool_overview)
            )
        )
        MoviesContent.addMovie(
            MovieModel(
                getString(R.string.guardians_galaxy_title),
                R.drawable.guardians_galaxy_poster,
                getString(R.string.guardians_galaxy_overview)
            )
        )
        MoviesContent.addMovie(
            MovieModel(
                getString(R.string.avengers_title),
                R.drawable.avengers_poster,
                getString(R.string.avengers_overview)
            )
        )
        MoviesContent.addMovie(
            MovieModel(
                getString(R.string.interstellar_title),
                R.drawable.interstellar_poster,
                getString(R.string.interstellar_overview)
            )
        )
        MoviesContent.addMovie(
            MovieModel(
                getString(R.string.jurassic_world_title),
                R.drawable.jurassic_world_poster,
                getString(R.string.jurassic_world_overview)
            )
        )
        MoviesContent.addMovie(
            MovieModel(
                getString(R.string.oceans_8_title),
                R.drawable.oceans_8_poster,
                getString(R.string.oceans_8_overview)
            )
        )
        MoviesContent.addMovie(
            MovieModel(
                getString(R.string.the_first_purge_title),
                R.drawable.the_first_purge_poster,
                getString(R.string.the_first_purge_overview)
            )
        )
        MoviesContent.addMovie(
            MovieModel(
                getString(R.string.thor_ragnarok_title),
                R.drawable.thor_ragnarok_poster,
                getString(R.string.thor_ragnarok_overview)
            )
        )
    }

    private fun initRecyclerView(rcvMoviesList: RecyclerView) {
        context?.let {
            rcvMoviesList.layoutManager = LinearLayoutManager(it)
            val moviesAdapter = MoviesViewAdapter(it, this)
            rcvMoviesList.adapter = moviesAdapter
            moviesAdapter.setData(MoviesContent.getMovies())
        }
    }

    override fun onMovieClicked(movie: MovieModel) {
        listener?.onMovieClicked(movie)
    }
}
