package com.family.lgtv

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.webkit.WebViewAssetLoader

class MainActivity : Activity() {
    private lateinit var web: WebView
    override fun onCreate(b: Bundle?) {
        super.onCreate(b)
        val loader = WebViewAssetLoader.Builder()
            .addPathHandler("/assets/", WebViewAssetLoader.AssetsPathHandler(this)).build()
        web = WebView(this).apply {
            setBackgroundColor(0xFF000000.toInt())
            settings.javaScriptEnabled = true
            settings.mediaPlaybackRequiresUserGesture = false
            settings.domStorageEnabled = true
            settings.useWideViewPort = false
            settings.loadWithOverviewMode = false
            settings.textZoom = 100
            settings.setSupportZoom(false)
            overScrollMode = View.OVER_SCROLL_NEVER
            webViewClient = object : WebViewClient() {
                override fun shouldInterceptRequest(v: WebView, r: WebResourceRequest) = loader.shouldInterceptRequest(r.url)
            }
            loadUrl("https://appassets.androidplatform.net/assets/www/index.html")
        }
        setContentView(web)
        web.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        web.requestFocus()
    }
    // Kutu uykudan uyanınca her zaman baştan (açılış animasyonu + ana menü) başla
    override fun onRestart() { super.onRestart(); web.reload() }

    // Back returns game -> menu -> home, and never leaves the app
    override fun onBackPressed() { web.evaluateJavascript("window.onRemoteBack&&window.onRemoteBack()", null) }
}
