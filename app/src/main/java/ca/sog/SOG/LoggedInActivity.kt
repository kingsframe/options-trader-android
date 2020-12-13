package ca.sog.SOG

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import okhttp3.*
import okio.IOException
import kotlinx.android.synthetic.main.activity_logged_in.*
import org.json.JSONObject
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.mutableListOf as mutableListOf

class LoggedInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in)

        var responseAccountsList = mutableListOf<QuestAccount>()

        val tokenBundle = intent.extras
        val tokensList = tokenBundle?.getStringArrayList("tokens") ?: ArrayList<String>()

        val access_token = tokensList[0]
//        val refresh_token = tokensList[1]
//        val token_type = tokensList[2]
//        val expires_in = tokensList[3]
        val api_server = tokensList[4]

//        GET /v1/accounts HTTP/1.1
//        Host: https://api01.iq.questrade.com
//        Authorization: Bearer C3lTUKuNQrAAmSD/TPjuV/HI7aNrAwDp
        GlobalScope.launch (Dispatchers.IO){
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url(api_server + "v1/accounts")
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
                        val accountsJSONlist = responseBodyJson.getJSONArray("accounts") //User may have more than 1 account! want to select them all here
                        val gson = GsonBuilder().create()
                        val responseAccountsArray = gson.fromJson(accountsJSONlist.toString(), Array<QuestAccount>::class.java)
                        responseAccountsList = responseAccountsArray.toCollection(mutableListOf())
                    }
                }
            }

            launch(Dispatchers.Main){
                val accountsAdapter = AccountAdapter(responseAccountsList)
                val accountsRecyclerView: RecyclerView = findViewById(R.id.accountsRecycleView)
                accountsRecyclerView.adapter = accountsAdapter
                accountsRecyclerView.layoutManager = LinearLayoutManager(this@LoggedInActivity)
            }
        }


//        TODO on account selected, get account number and and call accounts/:id/positions
//        GET https://api01.iq.questrade.com/v1/accounts/26598145/positions

//        {
//            "positions": [
//            {
//                "symbol": "THI.TO",
//                "symbolId": 38738,
//                "openQuantity": 100,
//                "currentMarketValue": 6017,
//                "currentPrice": 60.17,
//                "averageEntryPrice": 60.23,
//                "closedPnl": 0,
//                "openPnl": -6,
//                "totalCost": false,
//                "isRealTime": "Individual",
//                "isUnderReorg": false
//            }
//            ]
//        }

    }
}