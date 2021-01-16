package ca.sog.SOG

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.account_item.*
import kotlinx.android.synthetic.main.activity_accounts.*
import kotlinx.android.synthetic.main.activity_positions.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
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

        val responsePositionsList = mutableListOf<Positions>()

        val positionsAdapter = PositionsAdapter(responsePositionsList, this)
        positionsRecycleView.adapter = positionsAdapter

        Toast.makeText(this, "account number ${accountNumber}", Toast.LENGTH_LONG).show()

        // GET https://api01.iq.questrade.com/v1/accounts/26598145/positions
        // https://square.github.io/okhttp/recipes/
        // Request{method=GET, url=https://api07.iq.questrade.com/v1/accounts/52255509/positions, headers=[Authorization:Bearer dapokimQeyJ8KJFSp4DgB6QcZwF8CGFp0]}
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(api_server + "v1/accounts/${accountNumber}/positions")
                .header("Authorization", "Bearer $access_token")
                .build()
        lateinit var responseBodyJson: JSONObject

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (response.isSuccessful) {
                        val responseBody = response.body
                        val responseBodyString = responseBody?.string() ?: "NULL"
                        responseBodyJson = JSONObject(responseBodyString)
                        val positionsJSONlist = responseBodyJson.getJSONArray("positions")
                        val gson = GsonBuilder().create()
                        val responsePositionsArray = gson.fromJson(positionsJSONlist.toString(), Array<Positions>::class.java)
                        responsePositionsList.addAll(responsePositionsArray.toCollection(mutableListOf()))

                        this@PositionsActivity.runOnUiThread(Runnable { positionsAdapter.notifyDataSetChanged() })
                    }
                }
            }
            /*GlobalScope.launch (Dispatchers.IO) {
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
                        val responseBody = response.body
                        val responseBodyString = responseBody?.string() ?: "NULL"
                        responseBodyJson = JSONObject(responseBodyString)
                        val positionsJSONlist = responseBodyJson.getJSONArray("positions")
                        val gson = GsonBuilder().create()
                        val responsePositionsArray = gson.fromJson(positionsJSONlist.toString(), Array<Positions>::class.java)
                        responsePositionsList.addAll(responsePositionsArray.toCollection(mutableListOf()))
                    }
                }

                launch(Dispatchers.Main){
                    positionsAdapter.notifyDataSetChanged()
                }

            }

        }*/
        })
    }
}



