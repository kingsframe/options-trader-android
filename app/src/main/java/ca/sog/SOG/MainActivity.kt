package ca.sog.SOG

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import okio.IOException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    //private lateinit var textViewResult: TextView

    var accessToken: String = ""
    var apiServer: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //textViewResult = findViewById(R.id.text_view_result)
        val client = OkHttpClient()
        val refresh_token = "21MjvELCdvA_XishUVIBemwnr1Tl5saT0" //TODO hardcode

        //https://reqres.in/api/users?page=2

        val refreshCallback = object : Callback {
            //cannot not make http requests on mainthread
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (response.isSuccessful) {
                        //val myResponse = response.body
                        //TODO is this necessary
                        val jsonstr = response.body?.string() ?: "NULL"
                        val json: JSONObject = JSONObject(jsonstr)
                        accessToken = json.getString("access_token")
                        apiServer = json.getString("api_server")


                        this@MainActivity.runOnUiThread(object : Runnable {
                            override fun run() {
                                access_token.text = accessToken
                            }

                        })
                    }
                }
            }
        }

        val refreshRequest = Request.Builder()
            .url("https://login.questrade.com/oauth2/token?grant_type=refresh_token&refresh_token=" + refresh_token)
            .build()

        client.newCall(refreshRequest).enqueue(refreshCallback)

        val accessRequest = Request.Builder().url(apiServer + "v1/accounts")
            .header("Authorization", "Bearer " + accessToken)
            .build()

        val accessCallback = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                //val myResponse = response.body
                //TODO look responsebody type and access json fields
                val jsonstr = response.body?.string() ?: "NULL"
                val json: JSONObject = JSONObject(jsonstr)
                val account = json.getJSONArray("accounts").getJSONObject(0)
                val accType = account.getString("type")
                val accNum = account.getInt("number")


                this@MainActivity.runOnUiThread(object : Runnable {
                    override fun run() {
                        acc_type.text = accType
                        acc_num.text = accNum.toString()
                    }
                })
            }
        }

        client.newCall(accessRequest).enqueue(accessCallback)

    }


}