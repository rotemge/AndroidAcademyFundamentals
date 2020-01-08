package com.android.academy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnMovieClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.activity_main_frame, MoviesFragment()).commit()
        }
    }

    override fun onMovieClicked(movie: MovieModel) {
        val movieIndex = MoviesContent.getIndexOfMovie(movie)
        if (activity_main_tablet_pager == null) {
            //Phone mode
            val detailsFragment = DetailsViewPager.newInstance(movieIndex)
            supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.activity_main_frame, detailsFragment)
                .commit()
        } else {
            // Wide screen mode
            (activity_main_tablet_pager as DetailsViewPager).moveToItem(movieIndex)
        }
    }
}
