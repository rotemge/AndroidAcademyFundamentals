package com.android.academy.async_counter

import android.os.Handler
import android.os.Looper

class MySimpleAsyncTask(private val asyncTaskEvents: IAsyncTaskEvents) {

    var canceled: Boolean = false
        private set
    private var worker: Thread? = null

    private fun onPreExecute() = asyncTaskEvents.onPreExecute()

    private fun doInBackground(vararg params: Int?): String? {
        val startValue = if (params.isNotEmpty()) params[0] ?: 1 else 1
        for (number in startValue..10) {
            publishProgress(number)
            Thread.sleep(500)
        }
        return "Done!"
    }

    private fun onPostExecute(result: String?) {
        result?.let(asyncTaskEvents::onPostExecute)
    }

    fun execute(vararg params: Int?) {
        runOnMainThread(Runnable {
            onPreExecute()
            worker = Thread {
                try {
                    val result = doInBackground(*params)
                    runOnMainThread(Runnable {
                        onPostExecute(result)
                    })
                } catch (e: InterruptedException) { }
            }.also { it.start() }
        })
    }

    private fun onProgressUpdate(vararg values: Int?) {
        values[0]?.let(asyncTaskEvents::onProgressUpdate)
    }

    private fun publishProgress(progress: Int) {
        runOnMainThread(Runnable {
            onProgressUpdate(progress)
        })
    }

    fun cancel() {
        if (!canceled) {
            worker?.interrupt()
            canceled = true
            runOnMainThread(Runnable { onCancelled() })
        }
    }

    private fun onCancelled() = asyncTaskEvents.onCancel()

    private fun runOnMainThread(runnable: Runnable) {
        Handler(Looper.getMainLooper()).post(runnable)
    }

}