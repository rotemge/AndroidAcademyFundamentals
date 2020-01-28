package com.android.academy

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel(
    val id: Int,
    val name: String,
    val posterImage: String,
    val overview: String,
    val headerImage: String,
    // val trailerLink: String,
    val releaseDate: String
): Parcelable
