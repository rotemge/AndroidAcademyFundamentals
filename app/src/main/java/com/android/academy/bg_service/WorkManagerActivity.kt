package com.android.academy.bg_service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.work.*
import com.android.academy.R
import kotlinx.android.synthetic.main.activity_work_manager.*
import java.util.*

class WorkManagerActivity : AppCompatActivity() {

    companion object {
        const val PROGRESS_UPDATE_ACTION: String = "PROGRESS_UPDATE_ACTION"
        const val PROGRESS_VALUE_KEY: String = "PROGRESS_VALUE_KEY"
        const val WORKER_STATUS: String = "WORKER_STATUS"
    }

    private var backgroundProgressReceiver = BackgroundProgressReceiver()
    private var currentWork: UUID? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_manager)
        setButtons()
    }

    override fun onResume() {
        super.onResume()
        //subscribe for progress updates
        registerReceiver(backgroundProgressReceiver, IntentFilter(PROGRESS_UPDATE_ACTION))
    }

    public override fun onPause() {
        unregisterReceiver(backgroundProgressReceiver)
        super.onPause()
    }

    override fun onDestroy() {
        if (isFinishing) {
            cancelCurrentWork()
        }
        super.onDestroy()
    }

    private fun setButtons() {
        btnEnqueueWork.setOnClickListener {
            if (currentWork == null) {
                val workRequest = buildWorkRequest(buildConstraints())
                currentWork = workRequest.id
                WorkManager.getInstance(this).enqueue(workRequest)
            }
        }
        btnCancelWork.setOnClickListener {
            cancelCurrentWork()
        }
    }

    private fun buildConstraints() = Constraints.Builder().apply {
        if (networkSwitch.isChecked) {
            setRequiredNetworkType(NetworkType.CONNECTED)
        }
        setRequiresCharging(chargingSwitch.isChecked)
        setRequiresBatteryNotLow(batterySwitch.isChecked)
    }.build()

    private fun buildWorkRequest(constraints: Constraints) = OneTimeWorkRequest
        .Builder(HardWorker::class.java)
        .setConstraints(constraints)
        .build()

    fun cancelCurrentWork() {
        currentWork?.let {
            WorkManager.getInstance(this).cancelWorkById(it)
            currentWork = null
        }
    }

    inner class BackgroundProgressReceiver: BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val progress = intent.getIntExtra(PROGRESS_VALUE_KEY, -1)
            if (progress >= 0) {
                tvServiceProgress.text = when (progress) {
                    100 -> {
                        currentWork = null
                        "Done!"
                    }
                    else -> getString(R.string.service_progress_template, progress)
                }
            }

            intent.getStringExtra(WORKER_STATUS)?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }

    }

}
