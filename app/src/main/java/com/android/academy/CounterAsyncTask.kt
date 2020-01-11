package com.android.academy

import android.os.AsyncTask

class CounterAsyncTask(private val asyncTaskEvents: IAsyncTaskEvents): AsyncTask<Int, Int, String>() {

    override fun onPreExecute() = asyncTaskEvents.onPreExecute()

    override fun doInBackground(vararg params: Int?): String? {
        val startValue = if (params.isNotEmpty()) params[0] ?: 1 else 1
        for (number in startValue..10) {
            publishProgress(number)
            Thread.sleep(500)
        }
        return "Done!"
    }

    override fun onPostExecute(result: String?) {
        result?.let(asyncTaskEvents::onPostExecute)
    }

    override fun onProgressUpdate(vararg values: Int?) {
        values[0]?.let(asyncTaskEvents::onProgressUpdate)
    }

    override fun onCancelled() = asyncTaskEvents.onCancel()

}