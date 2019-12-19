package com.android.academy

import androidx.annotation.DrawableRes

data class MovieModel(
    val name: String,
    @DrawableRes val imageRes: Int,
    val overview: String?
)
