package com.aryandi.university.feature.detail

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.aryandi.university.ui.theme.UniversityTheme

class UniversityWebActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UniversityTheme {
                Scaffold { innerPadding ->
                    WebViewScreen(
                        modifier = Modifier.padding(innerPadding),
                        intent.getStringExtra("webPage").toString()
                    )
                }
            }
        }
    }

    @Composable
    fun WebViewScreen(modifier: Modifier, webPage: String) {
        AndroidView(
            modifier = modifier,
            factory = { context ->
                WebView(context).apply {
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.setSupportZoom(true)
                    settings.safeBrowsingEnabled = false
                }
            },
            update = { webView ->
                webView.loadUrl(webPage)
            }
        )
    }
}