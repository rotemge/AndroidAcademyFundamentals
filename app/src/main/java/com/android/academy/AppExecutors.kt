package com.android.academy

import android.os.Handler
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import android.os.Looper

object AppExecutors {

    private const val T_COUNT: Int = 3
    val diskIO = Executors.newSingleThreadExecutor()
    val networkIO = Executors.newFixedThreadPool(T_COUNT)
    val mainThread: Executor = MainThreadExecutor()

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

}