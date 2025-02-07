package com.example.webview

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar


class MyWebViewClient(private val progressBar: ProgressBar) : WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        Log.d("MyWebViewClient", "Начало загрузки страницы: $url")
        progressBar.visibility = View.VISIBLE
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        Log.d("MyWebViewClient", "Страница загружена: $url")
        progressBar.visibility = View.GONE
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        Log.d("MyWebViewClient", "Переход по ссылке: $url")
        url?.let { view?.loadUrl(it) }
        return true
    }

    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        Log.e("MyWebViewClient", "Ошибка загрузки: ${error?.description}")
    }
}

