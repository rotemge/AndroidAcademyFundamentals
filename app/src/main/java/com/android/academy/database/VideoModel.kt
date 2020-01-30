package com.android.academy.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class VideoModel (
    @PrimaryKey val movieId: Int,
    val id: String,
    val key: String
)