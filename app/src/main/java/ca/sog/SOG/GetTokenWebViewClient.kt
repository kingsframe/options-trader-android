package ca.sog.SOG

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity

class GetTokenWebViewClient(val context: Context) : WebViewClient() { //Everytime we init this we need to give it "this"

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean { //Starts activity
        if (Uri.parse(url).host != "www.example.com") {
            // This is my web site, so do not override; let my WebView load the page
            return false
        }
        // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs

        val tokenUrl = Uri.parse(url)
        val startAppIntent = Intent(context, LoggedInActivity::class.java).apply{
            putExtra("tokenURL", tokenUrl)
        }
        val emptyBundle : Bundle = Bundle()

        startActivity(context, startAppIntent, emptyBundle) //Immediately starts LoggedInActivity upon finding proper URL

//        Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
//            ContextCompat.startActivity(this)
//        }
        return true //If true returned, close startScreenActivity
    }
}