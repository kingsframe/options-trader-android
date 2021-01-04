package ca.sog.SOG

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.account_item.*
import kotlinx.coroutines.Dispatchers
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

        val accountInfo = intent.extras
        val accountNumber = accountInfo?.getString("accountNumber") ?: String()
        val access_token = accountInfo?.getString("access_token") ?: String()
        val api_server = accountInfo?.getString("api_server") ?: String()

        val positions: TextView = findViewById((R.id.positions))

        Toast.makeText(this,"account number ${accountNumber}",Toast.LENGTH_LONG).show()

        // GET https://api01.iq.questrade.com/v1/accounts/26598145/positions
        // https://square.github.io/okhttp/recipes/
        // Request{method=GET, url=https://api07.iq.questrade.com/v1/accounts/52255509/positions, headers=[Authorization:Bearer dapokimQeyJ8KJFSp4DgB6QcZwF8CGFp0]}
        GlobalScope.launch (Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url(api_server + "v1/accounts/${accountNumber}/positions")
                    .header("Authorization", "Bearer $access_token")
                    .build()

            lateinit var responseBodyJson: JSONObject
            client.newCall(request).execute().use{ response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                response.use {
                    if (response.isSuccessful) {
                        positions.text = response.body?.string()
                    }
                }

            }


        }

        /*
        //lateinit var responseBodyJson: JSONObject
        response.use {
                val responseBody = response.body
                val responseBodyString = responseBody?.string() ?: "NULL"
                responseBodyJson = JSONObject(responseBodyString)
                val gson = GsonBuilder().create()
            }*/

            //symbol.text = response.toString()
            //symbolId.text = "Hello World!"
        }

    }



