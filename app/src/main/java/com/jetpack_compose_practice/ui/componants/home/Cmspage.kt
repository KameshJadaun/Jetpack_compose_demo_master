package com.jetpack_compose_practice.ui.componants.home

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.jetpack_compose_practice.ui.theme.White
import com.jetpack_compose_practice.ui.theme.Yellow700
import kotlinx.coroutines.launch

@Composable
fun CmsPage(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About Us", color = White) },
                backgroundColor = Yellow700,
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, "backIcon")
                    }
                },
            )
        },
        content = { webView() })

}

@Composable
fun webView() {
    // Declare a string that contains a url
    val mUrl = "https://www.bhaskar.com/"

    // Adding a WebView inside AndroidView
    // with layout as full screen
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl(mUrl)
        }
    }, update = {
        it.loadUrl(mUrl)
    })
}
