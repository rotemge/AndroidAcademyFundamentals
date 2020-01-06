package com.android.academy

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel(
    val name: String,
    @DrawableRes val imageRes: Int,
    val overview: String?
    // TODO header image
    // TODO trailer link
    // TODO release date
): Parcelable
