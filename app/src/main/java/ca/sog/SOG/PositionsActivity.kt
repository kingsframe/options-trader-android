package ca.sog.SOG

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.account_item.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException


class PositionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_positions)
        setSupportActionBar(findViewById(R.id.toolbar))

        val tokenBundle = intent.extras
        val tokensList = tokenBundle?.getStringArrayList("tokens") ?: ArrayList<String>()

        val access_token = tokensList[0]
        val api_server = tokensList[4]

        val symbol: TextView = findViewById((R.id.sym))
        val symbolId: TextView = findViewById((R.id.symId))

        Toast.makeText(this,"account number ${accountNumber}",Toast.LENGTH_LONG).show()

        // GET https://api01.iq.questrade.com/v1/accounts/26598145/positions

        // why GlobalScope.launch?
        GlobalScope.launch {
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url(api_server + "v1/accounts/${accountNumber}")
                    .header("Authorization", "Bearer $access_token")
                    .build()

            lateinit var responseBodyJson: JSONObject
            client.newCall(request).execute().use{ response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                response.use {
                    val responseBody = response.body
                    val responseBodyString = responseBody?.string() ?: "NULL"
                    responseBodyJson = JSONObject(responseBodyString)
                    val gson = GsonBuilder().create()
                }

                symbol.text = response.toString()
                symbolId.text = "Hello World!"
            }

        }

    }
}