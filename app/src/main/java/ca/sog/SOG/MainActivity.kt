package ca.sog.SOG

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val questTradeAuthWebView: WebView = findViewById(R.id.webview)
        questTradeAuthWebView.webViewClient = QuestAuthWebViewClient(this)
        questTradeAuthWebView.loadUrl(
            "https://login.questrade.com/oauth2/authorize?client_id=y4OydeHFkQTSsD1FoQBW-EOPsABwgQ&response_type=token&redirect_uri=https://www.example.com"
        )
    }

}