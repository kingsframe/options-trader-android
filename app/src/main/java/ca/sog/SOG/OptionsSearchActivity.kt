package ca.sog.SOG

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_accounts.*
import kotlinx.android.synthetic.main.activity_options_search.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.ArrayList

//TODO we are given in bundle:
//"symbol": "TSM",
//"symbolId": 38052,

class OptionsSearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options_search)

        val symbolId = intent.extras?.getInt("symbolId")
        val symbolName = intent.extras?.getString("symbolName")
        val tokensList = intent.extras?.getStringArrayList("tokens") ?: ArrayList<String>()

        val access_token = tokensList[0]
        val refresh_token = tokensList[1]
        val token_type = tokensList[2]
        val expires_in = tokensList[3]
        val api_server = tokensList[4]


        val client = OkHttpClient()
        val request = Request.Builder()
                .url(api_server + "v1/symbols/" + symbolId + "/options")
                .header("Authorization", "Bearer $access_token")
                .build()
        lateinit var responseBodyJson: JSONObject

        client.newCall(request).enqueue(object: Callback {

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (response.isSuccessful) {
                        val responseBody = response.body
                        val responseBodyString = responseBody?.string() ?: "NULL"
                        responseBodyJson = JSONObject(responseBodyString)
                        val optionChainJsonList = responseBodyJson.getJSONArray("optionChain")
                        val gson = GsonBuilder().create()
                        val optionChainArray = gson.fromJson(optionChainJsonList.toString(), Array<OptionChain>::class.java)
                        Log.d("json", optionChainArray.toString())

//                        this@TickerSearchActivity.runOnUiThread(kotlinx.coroutines.Runnable {
//                            responseList.addAll(tickersArray.toCollection(mutableListOf()))
//                            tickerAdapter.notifyDataSetChanged()
//                        }) //update the recyclerView
                        //create option date object and Recycle adapter and then notify createOption changed
                    }
                }
            }
        })



        supportActionBar?.title = "Select expiry date for $symbolName"
        optionSymbolID.text = symbolId.toString()
    }
}