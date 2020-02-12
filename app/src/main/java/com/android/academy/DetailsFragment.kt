package com.android.academy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.android.academy.database.AppDatabase
import com.android.academy.download.DownloadActivity
import com.android.academy.networking.RestClient
import com.android.academy.networking.VideosListResult
import com.android.academy.networking.toVideoModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    private lateinit var headerImage: ImageView
    private lateinit var downloadButton: ImageButton
    private lateinit var posterImage: ImageView
    private lateinit var titleText: TextView
    private lateinit var releaseDateText: TextView
    private lateinit var trailerButton: Button
    private lateinit var overviewText: TextView
    private var movieModel: MovieModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        initViews(view)

        movieModel = arguments?.getParcelable(MOVIE_BUNDLE_KEY)
        movieModel?.let(::loadMovie)

        return view
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
        context?.let {
            val videoDao = AppDatabase.getInstance(it)?.videoDao()
            val videoModel = videoDao?.getVideo(movie.id)
            if (videoModel != null) {
                playTrailer(videoModel.key)
                return
            }
            trailerProgress.visibility = View.VISIBLE
            RestClient.moviesClient.loadMovieTrailer(movie.id).enqueue(object : Callback<VideosListResult> {
                override fun onFailure(call: Call<VideosListResult>, t: Throwable) {
                    trailerProgress.visibility = View.GONE
                    Toast.makeText(context, "Can't load trailer url", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<VideosListResult>, response: Response<VideosListResult>) {
                    trailerProgress.visibility = View.GONE
                    val video = response.body()?.toVideoModel()
                    if (video != null) {
                        videoDao?.insert(video)
                        playTrailer(video.key)
                    } else {
                        Toast.makeText(it, "Could not load trailer", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
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

}
