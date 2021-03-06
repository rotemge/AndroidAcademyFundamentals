package com.android.academy

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.academy.async_counter.AsyncTaskActivity
import com.android.academy.async_counter.ThreadsActivity
import com.android.academy.bg_service.BGServiceActivity
import com.android.academy.bg_service.WorkManagerActivity
import com.android.academy.database.DatabaseModule
import com.android.academy.model.MovieModel
import com.android.academy.model.MoviesContent
import com.android.academy.movie_details.DetailsViewPager
import com.android.academy.networking.MoviesListResult
import com.android.academy.networking.NetworkModule
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), OnMovieClickListener {

    companion object {
        private const val MOVIES_FRAGMENT_TAG = "MOVIES_FRAGMENT_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.activity_main_frame, MoviesFragment(), MOVIES_FRAGMENT_TAG).commit()
            loadMovies()
        }
    }

    private fun loadMovies() {
        AppExecutors.diskIO.execute {
            val cachedMovies = DatabaseModule.movieDao?.getAll()
            if (!cachedMovies.isNullOrEmpty()) {
                MoviesContent.fromList(cachedMovies)
                findMoviesFragment()?.updateList()
            }
        }


        NetworkModule.moviesClient.loadPopularMovies().enqueue(object : Callback<MoviesListResult> {
            override fun onFailure(call: Call<MoviesListResult>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Could not load movies", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<MoviesListResult>, response: Response<MoviesListResult>) {
                if (response.isSuccessful) {
                    response.body()?.let(MoviesContent::fromResults)
                    findMoviesFragment()?.updateList()
                    AppExecutors.diskIO.execute {
                        DatabaseModule.movieDao?.deleteAll()
                        DatabaseModule.movieDao?.insertAll(MoviesContent.getMovies())
                    }
                }
            }
        })
    }

    private fun findMoviesFragment(): MoviesFragment? {
        return supportFragmentManager.findFragmentByTag(MOVIES_FRAGMENT_TAG) as MoviesFragment?
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
        R.id.action_work_manager -> {
            startActivity(Intent(this, WorkManagerActivity::class.java))
            true
        }
        R.id.action_delete_movies_db -> {
            DatabaseModule.movieDao?.deleteAll()
            Toast.makeText(this, "Deleting movies list from DB", Toast.LENGTH_SHORT).show()
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
