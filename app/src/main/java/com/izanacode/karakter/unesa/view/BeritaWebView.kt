package com.izanacode.karakter.unesa.view

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import com.izanacode.karakter.unesa.R
import com.izanacode.karakter.unesa.utils.makeStatusBarTransparent

class BeritaWebView : AppCompatActivity() {
    private lateinit var myWebView: WebView
    private lateinit var context: Context
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_berita_web_view)
        makeStatusBarTransparent()

        context = this
        myWebView = findViewById(R.id.webview)
        progressBar = findViewById(R.id.pageLoadProgress)


        if (isNetworkAvailable()){
            showWebView()

        }else{
            finish()
        }

    }


    private fun isNetworkAvailable(): Boolean {
        val conManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = conManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun showWebView() {
        myWebView.apply {
            this.settings.loadsImagesAutomatically = true
            this.settings.javaScriptEnabled = false
            this.settings.useWideViewPort = true
            this.settings.loadWithOverviewMode = true
            this.settings.supportZoom()
            this.settings.builtInZoomControls = true
            this.settings.displayZoomControls = false
            this.webViewClient = WebViewClient()
            myWebView.loadUrl(intent.getStringExtra("link").toString())
        }

        myWebView.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = View.VISIBLE
                //swipeRefreshLayout.isRefreshing = false
            }

            override fun onPageCommitVisible(view: WebView?, url: String?) {
                super.onPageCommitVisible(view, url)
                progressBar.visibility = View.GONE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                try {
                    myWebView.stopLoading()
                }catch (e : Exception){

                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && myWebView.canGoBack()){
            myWebView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}