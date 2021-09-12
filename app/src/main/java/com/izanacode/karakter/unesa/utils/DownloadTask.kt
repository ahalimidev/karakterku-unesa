package com.izanacode.karakter.unesa.utils

import android.R
import android.app.DownloadManager
import android.os.Environment
import android.app.Activity
import android.app.ProgressDialog
import android.content.*
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.io.File
import java.lang.Exception

class DownloadTask(private val context: Context, downloadUrl: String) {
    private var downloadFileUrl = ""
    private var downloadFileName = ""
    private var progressDialog: ProgressDialog? = null
    var downloadID: Long = 0
    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            //Fetching the download id received with the broadcast
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {
                downloadCompleted(downloadID)
            }
        }
    }

    fun downloadFile(url: String?) {
        try {
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath,
                downloadFileName
            )
            val request = DownloadManager.Request(Uri.parse(url))
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) // Visibility of the download Notification
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    downloadFileName
                )
                .setDestinationUri(Uri.fromFile(file))
                .setTitle(downloadFileName) // Title of the Download Notification
                .setDescription("Downloading") // Description of the Download Notification
                .setAllowedOverMetered(true) // Set if download is allowed on Mobile network
                .setAllowedOverRoaming(true) // Set if download is allowed on roaming network
            request.allowScanningByMediaScanner()
            val downloadManager =
                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadID =
                downloadManager.enqueue(request) // enqueue puts the download request in the queue.
            progressDialog = ProgressDialog(context)
            progressDialog!!.setMessage("Downloading...")
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
        } catch (e: Exception) {
            Log.d("Download", e.toString())
        }
    }

    fun downloadCompleted(downloadID: Long) {
        progressDialog!!.dismiss()
        AlertDialog.Builder(context)
            .setTitle("Document")
            .setMessage("Document Downloaded Successfully")
            .setPositiveButton("Open") { dialog, which ->
                openDownloadedAttachment(downloadID)
            } // A null listener allows the button to dismiss the dialog and take no further action.
            .setNegativeButton(R.string.no) {
                    dialog: DialogInterface?, which: Int -> (context as Activity).finish()
            }
            .show()
        context.unregisterReceiver(onDownloadComplete)
    }

    var path: Uri? = null
    private fun openDownloadedAttachment(downloadId: Long) {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val query = DownloadManager.Query()
        query.setFilterById(downloadId)
        val cursor = downloadManager.query(query)
        if (cursor.moveToFirst()) {
            val downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
            val downloadLocalUri =
                cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
            val downloadMimeType =
                cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE))
            if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL && downloadLocalUri != null) {
                path = FileProvider.getUriForFile(
                    context, "com.izanacode.karakter.unesa.fileprovider", File(
                        Uri.parse(downloadLocalUri).path
                    )
                )
                //path = Uri.parse(downloadLocalUri);
                val pdfIntent = Intent(Intent.ACTION_VIEW)
                pdfIntent.setDataAndType(path, downloadMimeType)
                pdfIntent.flags =
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_CLEAR_TOP
                try {
                    context.startActivity(pdfIntent)
                    (context as Activity).finish()
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        context,
                        "No Application available to view PDF",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        cursor.close()
    }

    companion object {
        private const val TAG = "Download Task"
    }

    init {
        downloadFileUrl = downloadUrl
        downloadFileName = "Laporan Test E-Char.pdf"
        Log.e(TAG, downloadFileUrl)
        context.registerReceiver(
            onDownloadComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
        downloadFile(downloadFileUrl)
    }
}