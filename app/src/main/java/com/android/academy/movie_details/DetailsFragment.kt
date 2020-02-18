package com.android.academy.movie_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.academy.R
import com.android.academy.download.DownloadActivity
import com.android.academy.model.MovieModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details.*

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

    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var headerImage: ImageView
    private lateinit var downloadButton: ImageButton
    private lateinit var posterImage: ImageView
    private lateinit var titleText: TextView
    private lateinit var releaseDateText: TextView
    private lateinit var trailerButton: Button
    private lateinit var overviewText: TextView
    private var movieModel: MovieModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailsViewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        initViews(view)

        movieModel = arguments?.getParcelable(MOVIE_BUNDLE_KEY)
        movieModel?.let(::loadMovie)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        detailsViewModel.loadingLiveData().observe(viewLifecycleOwner, Observer(::showButtonLoading))
        detailsViewModel.trailerKeyLiveData().observe(viewLifecycleOwner, Observer(::playTrailer))
    }

    private fun loadMovie(movie: MovieModel) {
        titleText.text = movie.name
        overviewText.text = movie.overview
        releaseDateText.text = movie.releaseDate
        Picasso.get().load(movie.posterImage).placeholder(R.drawable.poster_placeholder).into(posterImage)
        Picasso.get().load(movie.headerImage).placeholder(R.drawable.header_placeholder).into(headerImage)
    }

    private fun initViews(view: View) {
        posterImage = view.findViewById(R.id.moviePoster)
        titleText = view.findViewById(R.id.movieTitle)
        releaseDateText = view.findViewById(R.id.releaseDate)
        headerImage = view.findViewById(R.id.movieHeader)
        overviewText = view.findViewById(R.id.overviewContent)
        trailerButton = view.findViewById(R.id.watchTrailerBtn)
        trailerButton.setOnClickListener(::watchTrailer)
        downloadButton = view.findViewById(R.id.downloadImageBtn)
        downloadButton.setOnClickListener(::downloadImage)
    }

    private fun watchTrailer(button: View) {
        val movie = movieModel ?: return
        detailsViewModel.fetchTrailer(movie.id)
    }

    private fun playTrailer(key: String) {
        val url = "https://www.youtube.com/watch?v=${key}"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun downloadImage(button: View) {
        val movie = movieModel ?: return
        context?.let {
            DownloadActivity.startActivity(it, movie)
        }
    }

    private fun showButtonLoading(loading: Boolean) {
        trailerProgress.visibility = if (loading) View.VISIBLE else View.GONE
    }

}
