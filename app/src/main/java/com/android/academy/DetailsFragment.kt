package com.android.academy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class DetailsFragment : Fragment() {

    companion object {

        private const val MOVIE_BUNDLE_KEY = "unique_movie_key"

        fun newInstance(movie: MovieModel): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putParcelable(MOVIE_BUNDLE_KEY, movie)
            fragment.arguments = args
            return fragment
        }

    }

    private lateinit var posterImage: ImageView
    private lateinit var titleText: TextView
    private lateinit var releaseDateText: TextView
    private lateinit var trailerButton: Button
    private lateinit var overviewText: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        initViews(view)

        val movie: MovieModel? = arguments?.getParcelable(MOVIE_BUNDLE_KEY)
        movie?.let(::loadMovie)

        return view
    }

    private fun loadMovie(movie: MovieModel) {
        titleText.text = movie.name
        overviewText.text = movie.overview
        posterImage.setImageResource(movie.imageRes)
    }

    private fun initViews(view: View) {
        posterImage = view.findViewById(R.id.moviePoster)
        titleText = view.findViewById(R.id.movieTitle)
        releaseDateText = view.findViewById(R.id.releaseDate)
        overviewText = view.findViewById(R.id.overviewContent)
        trailerButton = view.findViewById(R.id.watchTrailerBtn)
        trailerButton.setOnClickListener(::watchTrailer)
    }

    private fun watchTrailer(button: View) {
        activity?.let {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=6ZfuNTqbHE8"))
            if (intent.resolveActivity(it.packageManager) != null) {
                startActivity(intent)
            }
        }
    }

}
