package com.android.academy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_async_counter.*

class AsyncTaskActivity : AppCompatActivity(), IAsyncTaskEvents {

    private var task: CounterAsyncTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async_counter)
        tvCounter.text = getString(R.string.async_task_greeting)
        setButtons()
    }

    private fun setButtons() {
        btnCreate.setOnClickListener {
            task?.cancel(true)
            task = CounterAsyncTask(this)
            tvCounter.text = getString(R.string.async_task_created)
        }
        btnStart.setOnClickListener {
            task?.execute()
        }
        btnCancel.setOnClickListener {
            task?.cancel(true)
        }
    }

    override fun onPreExecute() {

    }

    override fun onPostExecute(result: String) {
        tvCounter.text = result
        task = null
    }

    override fun onProgressUpdate(num: Int) {
        tvCounter.text = num.toString()
    }

    override fun onCancel() {
        task = null
    }

}
