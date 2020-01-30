package com.android.academy

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class MovieModel(
    @PrimaryKey val id: Int,
    val name: String,
    val overview: String,
    val popularity: Double,
    val posterImage: String,
    val headerImage: String,
    // val trailerLink: String,
    val releaseDate: String
): Parcelable
