package com.android.academy

import android.os.AsyncTask

class CounterAsyncTask(private val asyncTaskEvents: IAsyncTaskEvents): AsyncTask<Any, Int, String>() {

    override fun onPreExecute() = asyncTaskEvents.onPreExecute()

    override fun doInBackground(vararg params: Any?): String {
        for (number in 1..10) {
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