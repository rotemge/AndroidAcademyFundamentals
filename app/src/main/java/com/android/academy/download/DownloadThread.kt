package com.android.academy.download

import android.content.Context
import android.os.Environment
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class DownloadThread(private val imageUrl: String, private val downloadCallBack: DownloadCallBack, private val context: Context) : Thread() {

    private var progress = 0
    private var lastUpdateTime: Long = 0

    override fun run() {
        val file = createFile()
        if (file == null) {
            downloadCallBack.onError("Can't create file")
            return
        }
        var connection: HttpURLConnection? = null
        var inputStream: InputStream? = null
        var fos: FileOutputStream? = null

        try {
            val url = URL(imageUrl)
            connection = url.openConnection() as HttpURLConnection
            connection.connect()

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                downloadCallBack.onError("Server returned HTTP response code: ${connection.responseCode} - ${connection.responseMessage}")
            }

            val fileLength = connection.contentLength

            // Input stream (Downloading file)
            inputStream = BufferedInputStream(url.openStream(), 8192)

            // Output stream (Saving file)
            fos = FileOutputStream(file.path)

            var next: Int
            val data = ByteArray(1024)
            while (inputStream.read(data).let { next = it; it != -1 }) {
                fos.write(data, 0, next)
                updateProgress(fos, fileLength)
            }
            downloadCallBack.onDownloadFinished(file.path)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            downloadCallBack.onError("MalformedURLException: ${e.message}")
        } catch (e: IOException) {
            e.printStackTrace()
            downloadCallBack.onError("IOException: ${e.message}")
        } finally {
            try {
                fos?.flush()
                fos?.close()
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            connection?.disconnect()
        }
    }

    private fun createFile(): File? {
        val externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) ?: return null
        val mediaStorageDirectory = File(externalFilesDir.path + File.separator)
        if (!mediaStorageDirectory.exists()) {
            if (!mediaStorageDirectory.mkdirs()) {
                return null
            }
        }
        val imageName = createImageFileName() + ".jpg"
        return File(mediaStorageDirectory.path + File.separator + imageName)
    }

    private fun createImageFileName(): String {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        return "FILE_$timeStamp"
    }

    private fun updateProgress(fos: FileOutputStream, fileLength: Int) {
        if (lastUpdateTime == 0L || System.currentTimeMillis() > lastUpdateTime + 500) {
            val count = fos.channel.size().toInt() * 100 / fileLength
            if (count > progress) {
                progress = count
                lastUpdateTime = System.currentTimeMillis()
                downloadCallBack.onProgressUpdate(progress)
            }
        }
    }

    interface DownloadCallBack {
        fun onProgressUpdate(percent: Int)
        fun onDownloadFinished(filePath: String)
        fun onError(error: String)
    }

}