package com.android.academy.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy



@Dao
interface VideoDao {

    @Query("SELECT * FROM VideoModel WHERE movieId = :movieId")
    fun getVideo(movieId: Int): VideoModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(videoModel: VideoModel)

}