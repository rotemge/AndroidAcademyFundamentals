package com.android.academy.database

import androidx.room.*
import com.android.academy.model.MovieModel
import com.android.academy.model.VideoModel

@Database(entities = [MovieModel::class, VideoModel::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao?
    abstract fun videoDao(): VideoDao?

}