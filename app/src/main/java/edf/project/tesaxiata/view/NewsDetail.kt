package edf.project.tesaxiata.view

import android.os.Bundle
import android.webkit.WebView
import edf.project.tesaxiata.R
import edf.project.tesaxiata.commons.RxBaseActivity
import edf.project.tesaxiata.commons.Utils

class NewsDetail : RxBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        val myWebView: WebView = findViewById(R.id.webview)
        myWebView.loadUrl(Utils.url_selected)
        myWebView.settings.javaScriptEnabled = true

    }

}