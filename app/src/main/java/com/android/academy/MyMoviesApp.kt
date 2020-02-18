package com.android.academy

import android.app.Application
import com.android.academy.database.DatabaseModule

class MyMoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        DatabaseModule.initialize(this)
    }

}