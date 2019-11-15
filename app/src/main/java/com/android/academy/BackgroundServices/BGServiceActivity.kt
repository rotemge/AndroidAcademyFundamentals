package com.android.academy.BackgroundServices

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.academy.R
import kotlinx.android.synthetic.main.activity_bgservice.*
import java.util.*


class BGServiceActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val PROGRESS_UPDATE_ACTION: String = "PROGRESS_UPDATE_ACTION"
        const val PROGRESS_VALUE_KEY: String = "PROGRESS_VALUE_KEY"
        const val SERVICE_STATUS: String = "SERVICE_STATUS"
    }

    private var backgroundProgressReceiver: BackgroundProgressReceiver? = null
    private var isServiceStarted: Boolean = false
    private var isIntentServiceStarted: Boolean = false
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bgservice)

        btn_start_service.setOnClickListener(this)
        btn_start_intent_service.setOnClickListener(this)
    }

    public override fun onResume() {
        super.onResume()
        subscribeForProgressUpdates()
    }


    public override fun onPause() {
        backgroundProgressReceiver?.let {
            unregisterReceiver(backgroundProgressReceiver)
        }
        super.onPause()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_start_service -> {
                if (isIntentServiceStarted) {
                    stopService(Intent(this, HardJobIntentService::class.java))
                    isIntentServiceStarted = false
                }
                if (!isServiceStarted) {
                    isServiceStarted = true
                    startService(Intent(this, HardJobService::class.java))
                }
            }

            R.id.btn_start_intent_service -> {
                if (isServiceStarted) {
                    stopService(Intent(this, HardJobService::class.java))
                    isServiceStarted = false
                }
                if (!isIntentServiceStarted) {
                    isIntentServiceStarted = true
                    startService(Intent(this, HardJobIntentService::class.java))
                }
            }
        }
    }

    private fun subscribeForProgressUpdates() {

        if (backgroundProgressReceiver == null) {
            backgroundProgressReceiver = BackgroundProgressReceiver()
        }
        val progressUpdateActionFilter = IntentFilter(PROGRESS_UPDATE_ACTION)
        registerReceiver(backgroundProgressReceiver, progressUpdateActionFilter)
    }

    inner class BackgroundProgressReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {

            val progress = intent.getIntExtra(PROGRESS_VALUE_KEY, -1)
            if (progress >= 0) {
                val text: String
                if (progress == 100) {
                    text = "Done!"
                    isIntentServiceStarted = false
                    isServiceStarted = false
                } else {
                    text = String.format(Locale.getDefault(), "%d%%", progress)
                }
                tv_progress_value.text = text
            }

            val msg = intent.getStringExtra(SERVICE_STATUS)
            if (msg != null) {
                toast?.cancel()
                toast = Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT)
                toast?.show()
            }

        }
    }

    public override fun onDestroy() {
        if (isFinishing) {
            if (isIntentServiceStarted) {
                stopService(Intent(this, HardJobIntentService::class.java))
            }
            if (isServiceStarted) {
                stopService(Intent(this, HardJobService::class.java))
            }
            toast?.cancel()
        }
        super.onDestroy()
    }

}