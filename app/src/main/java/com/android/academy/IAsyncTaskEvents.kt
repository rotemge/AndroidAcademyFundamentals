package com.android.academy

interface IAsyncTaskEvents {
    fun onPreExecute()
    fun onPostExecute(result: String)
    fun onProgressUpdate(num: Int)
    fun onCancel()
}