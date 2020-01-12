package com.android.academy.async_counter

interface IAsyncTaskEvents {
    fun onPreExecute()
    fun onPostExecute(result: String)
    fun onProgressUpdate(num: Int)
    fun onCancel()
}