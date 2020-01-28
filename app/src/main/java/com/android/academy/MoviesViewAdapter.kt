package com.android.academy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesViewAdapter(context: Context, private val movieClickListener: OnMovieClickListener) :
    RecyclerView.Adapter<MoviesViewAdapter.MovieItemViewHolder>() {

    private val layoutInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val asyncListDiffer = AsyncListDiffer<MovieModel>(this, MoviesDiffUtilCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val view = layoutInflater.inflate(R.layout.item_movie, parent, false)
        return MovieItemViewHolder(view, movieClickListener)
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])
    }

    fun setData(newItems: List<MovieModel>) {
        asyncListDiffer.submitList(newItems)
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
            Picasso.get().load(movieModel.posterImage).into(ivImage) //.placeholder(R.drawable.my_place_holder_image)
            tvTitle.text = movieModel.name
            tvOverview.text = movieModel.overview
            this.movieModel = movieModel
        }
    }

    inner class MoviesDiffUtilCallback : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            // As we don’t have Unique Ids
            return oldItem.hashCode() == newItem.hashCode()

        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.name == newItem.name
                    && oldItem.overview == newItem.overview
                    && oldItem.releaseDate == newItem.releaseDate
                    && oldItem.posterImage == newItem.posterImage
                    && oldItem.headerImage == newItem.headerImage

        }
    }
}