package com.example.webview

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import androidx.appcompat.app.AppCompatActivity
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)
        webView.settings.domStorageEnabled = true
        webView.settings.javaScriptEnabled = true

        progressBar = findViewById(R.id.progressBar)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        Log.d("WebViewActivity", "onCreate: Инициализация WebView")

        // Настройки WebView
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = MyWebViewClient(progressBar)

        val url = "https://stepchess.ru/"
        Log.d("WebViewActivity", "Загрузка URL: $url")
        webView.loadUrl(url)

        // Обновление страницы свайпом вниз
        swipeRefreshLayout.setOnRefreshListener {
            Log.d("WebViewActivity", "Обновление страницы")
            webView.reload()
        }

        // Включение SwipeRefresh только при прокрутке вверх
        webView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            val isAtTop = scrollY == 0
            swipeRefreshLayout.isEnabled = isAtTop
            Log.d("WebViewActivity", "Прокрутка страницы: scrollY = $scrollY, isAtTop = $isAtTop")
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            Log.d("WebViewActivity", "Назад в истории WebView")
            webView.goBack()
        } else {
            Log.d("WebViewActivity", "Выход из WebViewActivity")
            super.onBackPressed()
        }
    }
}
