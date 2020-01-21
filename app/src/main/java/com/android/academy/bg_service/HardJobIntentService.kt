package com.android.academy.bg_service

import android.app.IntentService
import android.content.Intent
import android.os.SystemClock
import android.widget.Toast
import com.android.academy.R

class HardJobIntentService: IntentService(TAG) {

    companion object {
        private const val TAG = "HardJobIntentService"
    }

    private var isDestroyed: Boolean = false

    override fun onHandleIntent(intent: Intent?) {
        isDestroyed = false
        showToast(getString(R.string.msg_starting_intent_service))
        var i = 0
        while (i <= 100 && !isDestroyed) {
            SystemClock.sleep(100)
            broadcastProgress(i)
            i++
        }
        broadcastStatus(getString(R.string.msg_finishing_intent_service))

    }

    override fun onDestroy() {
        isDestroyed = true
        super.onDestroy()
    }

    private fun broadcastProgress(progress: Int) {
        val intent = Intent(BGServiceActivity.PROGRESS_UPDATE_ACTION)
        intent.putExtra(BGServiceActivity.PROGRESS_VALUE_KEY, progress)
        sendBroadcast(intent)
    }

    private fun broadcastStatus(status: String) {
        val intent = Intent(BGServiceActivity.PROGRESS_UPDATE_ACTION)
        intent.putExtra(BGServiceActivity.SERVICE_STATUS, status)
        sendBroadcast(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}