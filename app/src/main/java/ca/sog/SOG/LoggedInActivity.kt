package ca.sog.SOG

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_logged_in.*

class LoggedInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in)
        val intent = intent
        val access_token = intent.extras?.getString("access_token")
        val refresh_token = intent.extras?.getString("refresh_token")
        val token_type = intent.extras?.getString("token_type")
        val expires_in = intent.extras?.getString("expires_in")
        val api_server = intent.extras?.getString("api_server")
        textView.text = "accesstoken = " + access_token + "  rt=" + refresh_token + token_type + " expire_in= " + expires_in + "  api_serv = " + api_server
    }
}