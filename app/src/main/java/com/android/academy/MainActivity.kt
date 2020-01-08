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
        val detailsFragment = DetailsViewPager.newInstance(MoviesContent.getIndexOfMovie(movie))
        supportFragmentManager
            .beginTransaction().apply {
                if (activity_main_tablet_frame == null) {
                    //Phone mode
                    addToBackStack(null)
                    replace(R.id.activity_main_frame, detailsFragment)
                } else {
                    // Wide screen mode
                    replace(R.id.activity_main_tablet_frame, detailsFragment)
                }
            }.commit()
    }
}
