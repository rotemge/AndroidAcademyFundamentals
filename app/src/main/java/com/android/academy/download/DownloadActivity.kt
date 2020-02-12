package com.android.academy.download

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android.academy.MovieModel
import com.android.academy.R
import kotlinx.android.synthetic.main.activity_download.*

class DownloadActivity : AppCompatActivity() {

    companion object {

        private const val PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE
        private const val PERMISSIONS_REQUEST_CODE = 453
        private const val MOVIE_MODEL_KEY = "MOVIE_MODEL_KEY"
        const val FILE_PATH_KEY = "FILE_PATH_KEY"

        fun startActivity(context: Context, movie: MovieModel) {
            val intent = Intent(context, DownloadActivity::class.java)
            intent.putExtra(MOVIE_MODEL_KEY, movie)
            context.startActivity(intent)
        }

    }

    private lateinit var  broadcastReceiver: BroadcastReceiver

    private val isPermissionGranted: Boolean
        get() = ContextCompat.checkSelfPermission(this, PERMISSION) == PackageManager.PERMISSION_GRANTED


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val filePath = intent?.getStringExtra(FILE_PATH_KEY)
                if (!filePath.isNullOrEmpty()) {
                    showImage(filePath)
                }
            }

            private fun showImage(filePath: String) {
                val bitmap = BitmapFactory.decodeFile(filePath)
                ivDownloadedImage.setImageBitmap(bitmap)
            }
        }

        if (isPermissionGranted) {
            startDownloadService()
        } else {
            requestPermission()
        }
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, IntentFilter(DownloadService.BROADCAST_ACTION))
    }

    override fun onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
        super.onStop()
    }

    private fun startDownloadService() {
        val movieModel = intent.getParcelableExtra<MovieModel>(MOVIE_MODEL_KEY)
        movieModel?.let {
            DownloadService.startService(this, it.headerImage)
        }
    }

    // ============================ PERMISSION REQUEST ============================

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSION)) {
            showExplainingRationaleDialog()
        } else {
            requestWritePermission()
        }
    }

    private fun showExplainingRationaleDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Ok") { _, _ -> requestWritePermission() }
        builder.setNegativeButton("Cancel") { _, _ -> finish() }
        builder.create().show()
    }

    private fun requestWritePermission() {
        ActivityCompat.requestPermissions(this, arrayOf(PERMISSION), PERMISSIONS_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode != PERMISSIONS_REQUEST_CODE) return
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Downloading now", Toast.LENGTH_SHORT).show()
            startDownloadService()
        } else {
            // Permission denied. Disable the functionality that depends on this permission.
            Toast.makeText(this, "Permission denied, can't download image.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

}
