package com.android.academy.database

import android.content.Context
import androidx.room.*
import com.android.academy.MovieModel

@Database(entities = [MovieModel::class, VideoModel::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "movies"
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

    abstract fun movieDao(): MovieDao?
    abstract fun videoDao(): VideoDao?

}