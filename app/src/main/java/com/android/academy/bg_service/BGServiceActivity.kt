package com.android.academy.bg_service

import android.content.*
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.academy.R
import kotlinx.android.synthetic.main.activity_bg_service.*

class BGServiceActivity : AppCompatActivity() {

    companion object {
        const val PROGRESS_UPDATE_ACTION: String = "PROGRESS_UPDATE_ACTION"
        const val PROGRESS_VALUE_KEY: String = "PROGRESS_VALUE_KEY"
        const val SERVICE_STATUS: String = "SERVICE_STATUS"
    }

    private var backgroundProgressReceiver = BackgroundProgressReceiver()
    private var currentService: ComponentName? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bg_service)
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
            killPreviousService()
        }
        super.onDestroy()
    }

    private fun setButtons() {
        btnStartService.setOnClickListener {
            startNewService(Intent(this, HardJobService::class.java))
        }
        btnStartIntentService.setOnClickListener {
            startNewService(Intent(this, HardJobIntentService::class.java))
        }
    }

    private fun startNewService(intent: Intent) {
        killPreviousService()
        currentService = startService(intent)
    }

    private fun killPreviousService() {
        currentService?.let {
            this.stopService(Intent(this, Class.forName(it.className)))
            currentService = null
        }
    }

    inner class BackgroundProgressReceiver: BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val progress = intent.getIntExtra(PROGRESS_VALUE_KEY, -1)
            if (progress >= 0) {
                tvServiceProgress.text = when (progress) {
                    100 -> {
                        currentService = null
                        "Done!"
                    }
                    else -> getString(R.string.service_progress_template, progress)
                }
            }

            intent.getStringExtra(SERVICE_STATUS)?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }

    }

}
