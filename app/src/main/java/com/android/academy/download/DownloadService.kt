package com.android.academy.download

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android.academy.R

class DownloadService : Service() {

    companion object {
        const val BROADCAST_ACTION: String = "com.academy.fundamentals.DOWNLOAD_COMPLETE"
        private const val ONGOING_NOTIFICATION_ID: Int = 14000605
        private const val CHANNEL_DEFAULT_IMPORTANCE = "Channel"
        private const val URL_KEY = "URL_KEY"

        fun startService(activity: Activity, url: String) {
            val intent = Intent(activity, DownloadService::class.java)
            intent.putExtra(URL_KEY, url)
            activity.startService(intent)
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground()
        val url = intent?.getStringExtra(URL_KEY)
        Toast.makeText(this, "DownloadService # URL: $url", Toast.LENGTH_SHORT).show()
        url?.let(::startDownloadThread)
        return START_STICKY
    }

    private fun startDownloadThread(url: String) {
        DownloadThread(url, object : DownloadThread.DownloadCallBack {

            override fun onProgressUpdate(percent: Int) {
                updateNotification(percent)
            }

            override fun onDownloadFinished(filePath: String) {
                sendBroadcastMsgDownloadComplete(filePath)
                stopSelf()
            }

            override fun onError(error: String) {
                stopSelf()
            }

        }, this).start()
    }

    private fun startForeground() {
        createNotificationChannel()
        startForeground(ONGOING_NOTIFICATION_ID, createNotification(0))
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val name = getString(R.string.download_channel_name)
        val description = getString(R.string.download_channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_DEFAULT_IMPORTANCE, name, importance)
        channel.description = description
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(progress: Int): Notification {
        val notificationIntent = Intent(this, DownloadActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val progressStr = getString(R.string.service_progress_template, progress)

        val notification = NotificationCompat.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
            .setContentTitle(getText(R.string.download_notification_title))
            .setContentText(progressStr)
            .setSmallIcon(android.R.drawable.stat_sys_upload)
            .setProgress(100, progress, false)
            .setContentIntent(pendingIntent)
            .build()
        notification.flags = Notification.FLAG_ONLY_ALERT_ONCE
        return notification
    }

    private fun updateNotification(progress: Int) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(ONGOING_NOTIFICATION_ID, createNotification(progress))
    }

    private fun sendBroadcastMsgDownloadComplete(filePath: String) {
        val intent = Intent(BROADCAST_ACTION)
        intent.putExtra(DownloadActivity.FILE_PATH_KEY, filePath)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

}
