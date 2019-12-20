package com.android.academy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesViewAdapter(context: Context, private val movieClickListener: OnMovieClickListener) :
    RecyclerView.Adapter<MoviesViewAdapter.MovieItemViewHolder>() {

    private val layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val movies = mutableListOf<MovieModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val view = layoutInflater.inflate(R.layout.item_movie, parent, false)
        return MovieItemViewHolder(view, movieClickListener)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun setData(newItems: List<MovieModel>) {
        movies.clear()
        movies.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class MovieItemViewHolder(view: View, movieClickListener: OnMovieClickListener) :
        RecyclerView.ViewHolder(view) {
        private val ivImage: ImageView = view.item_movie_iv
        private val tvTitle: TextView = view.item_movie_title_tv
        private val tvOverview: TextView = view.item_movie_overview_tv
        private lateinit var movieModel: MovieModel

        init {
            view.setOnClickListener {
                movieClickListener.onMovieClicked(movieModel)
            }
        }

        fun bind(movieModel: MovieModel) {
            ivImage.setImageResource(movieModel.imageRes)
            tvTitle.text = movieModel.name
            tvOverview.text = movieModel.overview
            this.movieModel = movieModel
        }
    }
}