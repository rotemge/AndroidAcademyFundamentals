package com.android.academy.database

import android.app.Application
import androidx.room.Room

object DatabaseModule {

    private const val DATABASE_NAME = "movies"
    var movieDao: MovieDao? = null
    var videoDao: VideoDao? = null

    fun initialize(application: Application) {
        val appDatabase = Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

        movieDao = appDatabase.movieDao()
        videoDao = appDatabase.videoDao()
    }

}