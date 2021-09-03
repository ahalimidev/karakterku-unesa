package com.izanacode.karakter.unesa.view

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.izanacode.karakter.unesa.R
import com.izanacode.karakter.unesa.utils.makeStatusBarTransparent


class VideoWebview : AppCompatActivity() {
    private lateinit var myWebView: WebView
    private lateinit var context: Context
    var datalink = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_webview)
        makeStatusBarTransparent()
        datalink = intent.getStringExtra("link").toString()
        context = this
        myWebView = findViewById(R.id.webview_youtube)


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
        val kodeHTML = "<head></head><body><iframe style=\" position: absolute;\n" +
                "  top: 0;\n" +
                "  left: 0;\n" +
                "  bottom: 0;\n" +
                "  right: 0;\n" +
                "  width: 100%;\n" +
                "  height: 100%\" src=\"https://www.youtube.com/embed/$datalink\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe></body>"
        myWebView.apply {
            this.settings.loadsImagesAutomatically = true
            this.settings.javaScriptEnabled = true
            this.settings.useWideViewPort = true
            this.settings.loadWithOverviewMode = true
            this.settings.supportZoom()
            this.settings.builtInZoomControls = true
            this.settings.displayZoomControls = false
            this.webViewClient = WebViewClient()
            myWebView.loadData(kodeHTML,"text/html; charset=utf-8",null)
        }

        myWebView.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageCommitVisible(view: WebView?, url: String?) {
                super.onPageCommitVisible(view, url)
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