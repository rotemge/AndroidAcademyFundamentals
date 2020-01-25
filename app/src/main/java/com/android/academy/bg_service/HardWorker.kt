package com.android.academy.bg_service

import android.content.Context
import android.content.Intent
import android.os.SystemClock
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.android.academy.R

class HardWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context.applicationContext, workerParams)  {

    override fun doWork(): Result {
        broadcastStatus(applicationContext.getString(R.string.msg_starting_worker_job))
        var i = 0
        while (i <= 100 && !isStopped) {
            SystemClock.sleep(100)
            broadcastProgress(i)
            i++
        }
        if (i < 100) {
            broadcastStatus(applicationContext.getString(R.string.msg_stopped_worker_job))
            return Result.failure()
        }
        broadcastStatus(applicationContext.getString(R.string.msg_finishing_worker_job))
        return Result.success()
    }

    fun broadcastProgress(progress: Int) {
        val intent = Intent(WorkManagerActivity.PROGRESS_UPDATE_ACTION)
        intent.putExtra(WorkManagerActivity.PROGRESS_VALUE_KEY, progress)
        applicationContext.sendBroadcast(intent)
    }

    fun broadcastStatus(status: String) {
        val intent = Intent(WorkManagerActivity.PROGRESS_UPDATE_ACTION)
        intent.putExtra(WorkManagerActivity.WORKER_STATUS, status)
        applicationContext.sendBroadcast(intent)
    }

}