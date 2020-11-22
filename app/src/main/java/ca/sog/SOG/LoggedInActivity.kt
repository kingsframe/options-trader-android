package ca.sog.SOG

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_logged_in.*

class LoggedInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in)

        val tokenBundle = intent.extras
        val partsList = tokenBundle?.getStringArrayList("tokens") ?: ArrayList<String>()

        //val intent = intent
        val access_token = partsList[0]
        val refresh_token = partsList[1]
        val token_type = partsList[2]
        val expires_in = partsList[3]
        val api_server = partsList[4]
        textView.text = "accesstoken = " + access_token + "  rt=" + refresh_token + token_type + " expire_in= " + expires_in + "  api_serv = " + api_server
    }
}