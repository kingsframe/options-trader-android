package ca.sog.SOG

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_accounts.*
import kotlinx.android.synthetic.main.activity_options_search.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

//TODO we are given in bundle:
//"symbol": "TSM",
//"symbolId": 38052,

class OptionsSearchActivity : AppCompatActivity() {
    val symbolId = intent.extras?.getInt("symbolId")
    val symbolName = intent.extras?.getString("symbolName")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options_search)

        supportActionBar?.title = symbolName
        optionSymbolID.text = symbolId.toString()
    }

    /*override fun onItemClicked(symbolId: Int, symbolName: String) {
        //val intent = Intent(this, OptionsChainActivity::class.java)
        intent.putExtra("symbolId", symbolId)
        intent.putExtra("symbolName", symbolName)
        startActivity(intent);
    }*/

    fun getExpiryDate(tokensList: ArrayList<String>, query: String) {
        val access_token = tokensList[0]
        val refresh_token = tokensList[1]
        val token_type = tokensList[2]
        val expires_in = tokensList[3]
        val api_server = tokensList[4]

        val client = OkHttpClient()

        // GET https://api01.iq.questrade.com/v1/symbols/9291/options
        val request = Request.Builder()
                .url(api_server + "v1/symbols/$symbolId/options")
                .header("Authorization", "Bearer $access_token")
                .build()
        lateinit var responseBodyJson: JSONObject

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            // Extract expiry date from json response
            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (response.isSuccessful) {
                        val responseBody = response.body
                        val responseBodyString = responseBody?.string() ?: "NULL"
                        responseBodyJson = JSONObject(responseBodyString)
                        val optionsJsonList = responseBodyJson.getJSONArray("options")
                        val gson = GsonBuilder().create()
                        val tickersArray = gson.fromJson(optionsJsonList.toString(), Array<Options>::class.java)

                        // TODO: Add appropriate recycler view
                    }
                }
            }
        })
    }
}