package com.android.academy.async_counter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.academy.R
import kotlinx.android.synthetic.main.activity_async_counter.*

class ThreadsActivity : AppCompatActivity(), IAsyncTaskEvents {

    companion object {
        private const val TASK_RUNNING_KEY = "unique_is_task_running_key"
        private const val COUNTER_VALUE_KEY = "unique_counter_value_key"
    }

    private var task: MySimpleAsyncTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async_counter)

        val isTaskRunning = savedInstanceState?.getBoolean(TASK_RUNNING_KEY, false) ?: false
        if (isTaskRunning) {
            task = MySimpleAsyncTask(this)
            val value = savedInstanceState?.getString(COUNTER_VALUE_KEY, "")?.toIntOrNull()
            task?.execute(value)
        } else {
            tvCounter.text = getString(R.string.async_task_greeting)
        }
        setButtons()
    }

    private fun setButtons() {
        btnCreate.setOnClickListener {
            task?.cancel()
            task = MySimpleAsyncTask(this)
            tvCounter.text = getString(R.string.async_task_created)
        }
        btnStart.setOnClickListener {
            task?.execute()
        }
        btnCancel.setOnClickListener {
            task?.cancel()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val isRunning = !(task?.canceled ?: true)
        outState.putBoolean(TASK_RUNNING_KEY, isRunning)
        if (isRunning) outState.putString(COUNTER_VALUE_KEY, tvCounter.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onPreExecute() { }

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

    override fun onDestroy() {
        super.onDestroy()
        task?.cancel()
        task = null
    }

}
