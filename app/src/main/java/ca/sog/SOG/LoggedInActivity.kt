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

        val tokenBundle = intent.extras
        val tokensList = tokenBundle?.getStringArrayList("tokens") ?: ArrayList<String>()

        val access_token = tokensList[0]
        val refresh_token = tokensList[1]
        val token_type = tokensList[2]
        val expires_in = tokensList[3]
        val api_server = tokensList[4]
//        textView.text = "accesstoken = " + access_token + "  rt=" + refresh_token + token_type + " expire_in= " + expires_in + "  api_serv = " + api_server

//         TODO make networking call to
//        GET /v1/accounts HTTP/1.1
//        Host: https://api01.iq.questrade.com
//        Authorization: Bearer C3lTUKuNQrAAmSD/TPjuV/HI7aNrAwDp
        var responseAccountsList = mutableListOf<QuestAccount>()
        GlobalScope.launch (Dispatchers.IO){
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url(api_server + "v1/accounts")
                    .header("Authorization", "Bearer " + access_token)
                    .build()

            lateinit var responseBodyJson: JSONObject
            client.newCall(request).execute().use{ response ->
                //cannot not make http requests on mainthread
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
                        //println(responseAccountsList)
                    }
                }
            }

            //Grab accounts
//            val userID = responseBodyJson.getInt("userId").toString() //outside of jsonArray
//
//            val accountsList = mutableListOf<QuestAccount>()
//            val accountsJSONlist = responseBodyJson.getJSONArray("accounts") //User may have more than 1 account! want to select them all here
//            for (i in 0 until accountsJSONlist.length()){
//                val curr = accountsJSONlist.getJSONObject(i)
//
//                val type = curr.getString("type")
//                val number = curr.getString("number")
//                val status = curr.getString("status")
//                val isPrimary = curr.getBoolean("isPrimary")
//                val isBilling = curr.getBoolean("isBilling")
//                val clientAccountType = curr.getString("clientAccountType")
//
//                val newAcc = QuestAccount(type, number, status, isPrimary, isBilling, clientAccountType)
//                accountsList.add(newAcc)
//            }




//            launch(Dispatchers.Main){
//                //textView.text = number
//            }
        }

//                {
//                    "accounts": [
//                    {
//                        "type": "Margin"
//                        "number":"26598145",
//                        "status": "Active",
//                        "isPrimary": true,
//                        "isBilling": true,
//                        "clientAccountType: "Individual"
//                    },
//                    ...
//                    ],
//                    "userId": 3000124
//                }

        var fakeAccountsList = mutableListOf(
                QuestAccount( "Margin", "fake123",  "Active",true,true, "Individual"),
                QuestAccount( "Margin", "fake123",  "Active",true,true, "Individual"),
                QuestAccount( "Margin", "fake123",  "Active",true,true, "Individual"),
                QuestAccount( "Margin", "fake123",  "Active",true,true, "Individual"),
                QuestAccount( "Margin", "fake123",  "Active",true,true, "Individual"),
                QuestAccount( "Margin", "fake123",  "Active",true,true, "Individual"),
                QuestAccount( "Margin", "fake123",  "Active",true,true, "Individual"),
                QuestAccount( "Margin", "fake123",  "Active",true,true, "Individual"),
        )

        val accountsAdapter = AccountAdapter(responseAccountsList)
        val accountsRecyclerView: RecyclerView = findViewById(R.id.accountsRecycleView)
        accountsRecyclerView.adapter = accountsAdapter
        accountsRecyclerView.layoutManager = LinearLayoutManager(this)


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