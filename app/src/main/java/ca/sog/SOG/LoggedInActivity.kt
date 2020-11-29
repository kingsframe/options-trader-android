package ca.sog.SOG

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import okhttp3.*
import okio.IOException
import kotlinx.android.synthetic.main.activity_logged_in.*
import org.json.JSONObject

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
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(api_server + "v1/accounts")
                .header("Authorization", "Bearer " + access_token)
                .build()

        lateinit var responseBodyJson : JSONObject
        client.newCall(request).enqueue(object : Callback {
            //cannot not make http requests on mainthread
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (response.isSuccessful) {
                        val responseBody = response.body
                        val responseBodyString = responseBody?.string() ?: "NULL"
                        responseBodyJson = JSONObject(responseBodyString)
                        //Can't run UI on network thread
                    }
                }
            }
        })

        //Critical Section problem: Options for synchronicity: Coroutine (execute in coroutine, add sempahores), Retrofit, Volley
        val accountJson = responseBodyJson.getJSONArray("accounts").getJSONObject(0) //User may have more than 1 account! want to select them all here
        val number = accountJson.getString("number")
        val userID = responseBodyJson.getInt("userId").toString()
        textView.text = number



//        TODO decide either to make List UI for accounts response OR
//        select the first account to call accounts/:id/positions
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