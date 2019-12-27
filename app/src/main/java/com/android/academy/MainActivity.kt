package com.android.academy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), OnMovieClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(R.id.activity_main_frame, MoviesFragment()).commit()
    }

    override fun onMovieClicked(movie: MovieModel) {
        supportFragmentManager.beginTransaction().replace(R.id.activity_main_frame, DetailsFragment()).commit()
    }
}
