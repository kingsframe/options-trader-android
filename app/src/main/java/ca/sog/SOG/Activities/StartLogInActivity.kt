package ca.sog.SOG.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import ca.sog.SOG.Classes.QuestAuthWebViewClient
import ca.sog.SOG.R

class StartLogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_log_in)

        val questTradeAuthWebView: WebView = findViewById(R.id.webview)
        questTradeAuthWebView.webViewClient = QuestAuthWebViewClient(this)
        questTradeAuthWebView.loadUrl(
            "https://login.questrade.com/oauth2/authorize?client_id=y4OydeHFkQTSsD1FoQBW-EOPsABwgQ&response_type=token&redirect_uri=https://www.example.com"
        )
    }
}