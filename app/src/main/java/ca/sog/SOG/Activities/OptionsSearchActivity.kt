package ca.sog.SOG.Activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import ca.sog.SOG.Classes.Tokens
import ca.sog.SOG.DataClass.ChainPerRoot
import ca.sog.SOG.DataClass.ExpirationDate
import ca.sog.SOG.ExpirationDateAdapter
import ca.sog.SOG.OnExpirationDateClickListener
import ca.sog.SOG.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_accounts.*
import kotlinx.android.synthetic.main.activity_options_search.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.*

//TODO we are given in bundle:
//"symbol": "TSM",
//"symbolId": 38052,

class OptionsSearchActivity : AppCompatActivity(), OnExpirationDateClickListener {
//    fun onItemClicked(expiryDate: Date, chainPerRoot: ChainPerRoot) {
//        //TODO("Not yet implemented")
//        Toast.makeText(applicationContext, "$expiryDate was clicked", Toast.LENGTH_SHORT).show()
//        val intent = Intent(this, TickerSearchActivity::class.java)
//        intent.putExtra("accountNumber", accountNumber)
//        startActivity(intent);
//    }

    override fun onItemClicked(expiryDate: Date, chainPerRoot: Array<ChainPerRoot>) {
        TODO("Not yet implemented")
        Toast.makeText(applicationContext, "$expiryDate was clicked", Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options_search)

        //setup recycler view
        val expirationDatesList = mutableListOf<ExpirationDate>()
        val expirationDatesAdapter = ExpirationDateAdapter(expirationDatesList, this)
        expirationDatesRecycleView.adapter = expirationDatesAdapter

        val symbolId = intent.extras?.getInt("symbolId")
        val symbolName = intent.extras?.getString("symbolName")
        //val tokensList = intent.extras?.getStringArrayList("tokens") ?: ArrayList<String>()

        val access_token = Tokens.accessToken
        val api_server = Tokens.apiServer

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
                        val optionChainArray = gson.fromJson(optionChainJsonList.toString(), Array<ExpirationDate>::class.java)
                        Log.d("json", optionChainArray.toString())
                        expirationDatesList.addAll(optionChainArray.toCollection(mutableListOf()))

                        this@OptionsSearchActivity.runOnUiThread(kotlinx.coroutines.Runnable {
                            expirationDatesAdapter.notifyDataSetChanged()
                        })
                    }
                }
            }
        })

        supportActionBar?.title = "Select expiry date for $symbolName"
    }


}