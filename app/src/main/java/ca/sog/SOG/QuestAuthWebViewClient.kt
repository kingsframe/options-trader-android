package ca.sog.SOG

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat.startActivity

class QuestAuthWebViewClient(private val context: Context) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (Uri.parse(url).host != "www.example.com") {
            // The redirect from quest trade is a dummy url www.example.com. If that is not the case, something is wrong
            return false
        }
        // quest trade will redirect us back to www.example.com/params, we will needs to params to finish the auth
        val startLoggedInActivityIntent = Intent(context, LoggedInActivity::class.java).apply{
            putExtra("tokenURL", Uri.parse(url))
        }
        startActivity(context, startLoggedInActivityIntent, Bundle())

        return true
    }
}