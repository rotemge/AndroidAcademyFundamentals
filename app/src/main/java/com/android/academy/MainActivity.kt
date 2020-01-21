package com.android.academy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.android.academy.async_counter.AsyncTaskActivity
import com.android.academy.async_counter.ThreadsActivity
import com.android.academy.bg_service.BGServiceActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnMovieClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            loadMovies()
            supportFragmentManager.beginTransaction().add(R.id.activity_main_frame, MoviesFragment()).commit()
        }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_async_task -> {
            startActivity(Intent(this, AsyncTaskActivity::class.java))
            true
        }
        R.id.action_thread_handler -> {
            startActivity(Intent(this, ThreadsActivity::class.java))
            true
        }
        R.id.action_background_service -> {
            startActivity(Intent(this, BGServiceActivity::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
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
