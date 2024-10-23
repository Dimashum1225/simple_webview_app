package com.example.webview

import android.graphics.Bitmap
import android.os.Bundle
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
        progressBar = findViewById(R.id.progressBar)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        window.statusBarColor = ContextCompat.getColor(this,R.color.white)

        // Настройки WebView
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView, url: String?) {
                progressBar.visibility = View.GONE
                swipeRefreshLayout.isRefreshing = false // Остановить индикатор обновления
            }

            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                progressBar.visibility = View.GONE

                swipeRefreshLayout.isRefreshing = false // Остановить индикатор обновления
            }

        }

        // Загрузка начального URL
        webView.loadUrl("YOUR_URL")

        // Обработка перетаскивания для обновления
        swipeRefreshLayout.setOnRefreshListener {
            webView.reload() // Перезагрузить страницу
        }
        webView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            // Если текущая позиция скролла == 0 (страница прокручена до самого верха)
            swipeRefreshLayout.isEnabled = scrollY == 0
        }

        // Обработка перетаскивания для обновления
        swipeRefreshLayout.setOnRefreshListener {
            webView.reload() // Перезагружаем страницу
        }

    }


    override fun onBackPressed() { //Думаю по названию понятно
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
