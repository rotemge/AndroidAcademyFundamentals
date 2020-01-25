package com.android.academy.bg_service

import android.app.Service
import android.content.Intent
import android.os.*
import com.android.academy.R

class HardJobService: Service() {

    companion object {
        const val TAG: String = "HardJobService"
    }

    private lateinit var serviceHandler: ServiceHandler
    private var isDestroyed: Boolean = false

    override fun onCreate() {
        super.onCreate()
        // To avoid cpu-blocking, we create a background handler to run our service
        val thread = HandlerThread(TAG, Process.THREAD_PRIORITY_BACKGROUND)
        // start the new handler thread
        thread.start()

        // start the service using the background handler
        serviceHandler = ServiceHandler(thread.looper)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        isDestroyed = false
        broadcastStatus(getString(R.string.msg_starting_service))

        // call a new service handler. The service ID can be used to identify the service
        val message = serviceHandler.obtainMessage()
        message.arg1 = startId
        serviceHandler.sendMessage(message)

        return START_STICKY
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

    private inner class ServiceHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            // Well calling serviceHandler.sendMessage(message) from onStartCommand this method will be called.

            // Add your cpu-blocking activity here
            var i = 0
            while (i <= 100 && !isDestroyed) {
                SystemClock.sleep(100)
                broadcastProgress(i)
                i++
            }
            broadcastStatus(getString(R.string.msg_finishing_service, msg.arg1))
            // the msg.arg1 is the startId used in the onStartCommand so we can track the running service here.
            stopSelf(msg.arg1)
        }
    }

}
