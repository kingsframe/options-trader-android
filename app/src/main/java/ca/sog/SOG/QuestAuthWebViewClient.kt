package ca.sog.SOG

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat.startActivity

class QuestAuthWebViewClient(private val context: Context) : WebViewClient() {

    fun parseToken(url: String?): List<String>? {
        var delimiter1 = "#"
        var delimiter2 = "&"
        val parts = url?.split(delimiter1, delimiter2)
        return parts
    }


    fun parseEqual (tokenWithName: String?) : String? {
        var delimiter = "="
        val parts = tokenWithName?.split(delimiter)
        return parts?.get(1)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (Uri.parse(url).host != "www.example.com") {
            // The redirect from quest trade is a dummy url www.example.com. If that is not the case, continue
            return false
        }
        // quest trade will redirect us back to www.example.com/params, we will needs to params to finish the auth
        // url should look like
        // https://www.example.com/#access_token=...&refresh_token=...&token_type= Bearer&expires_in=1800&api_server=https://api01.iq.questrade.com/

        var parts = parseToken(url)

        var access_token = parseEqual(parts?.get(1))
        var refresh_token = parseEqual(parts?.get(2))
        var token_type = parseEqual(parts?.get(3))
        var expires_in = parseEqual(parts?.get(4))
        var api_server = parseEqual(parts?.get(5))


        val startLoggedInActivityIntent = Intent(context, LoggedInActivity::class.java).apply{
            putExtra("access_token", access_token)
            putExtra("refresh_token", refresh_token)
            putExtra("token_type", token_type)
            putExtra("expires_in", expires_in)
            putExtra("api_server", api_server)
        }
        startActivity(context, startLoggedInActivityIntent, Bundle())

        return true
    }

}