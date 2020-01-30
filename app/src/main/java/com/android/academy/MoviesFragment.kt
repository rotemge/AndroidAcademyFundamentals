package com.android.academy

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment(), OnMovieClickListener {

    private var listener: OnMovieClickListener? = null
    private var moviesAdapter: MoviesViewAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_movies, container, false)
        initRecyclerView(view.findViewById(R.id.rvMoviesList))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateList()
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

    fun updateList() {
        moviesAdapter?.setData(MoviesContent.getMovies())
        if (MoviesContent.getCount() > 0) {
            view?.findViewById<ProgressBar>(R.id.moviesProgress)?.visibility = View.GONE
        }
    }

    private fun initRecyclerView(rcvMoviesList: RecyclerView) {
        context?.let {
            rcvMoviesList.layoutManager = LinearLayoutManager(it)
            moviesAdapter = MoviesViewAdapter(it, this)
            rcvMoviesList.adapter = moviesAdapter
        }
    }

    override fun onMovieClicked(movie: MovieModel) {
        listener?.onMovieClicked(movie)
    }
}
