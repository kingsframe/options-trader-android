package ca.sog.SOG

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import okio.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var textViewResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //textViewResult = findViewById(R.id.text_view_result)
        val client = OkHttpClient()
        val refresh_token = "LdrP14aUWVc6WdG24QGLXkf-PnoZDRTO0" //TODO hardcode
        val request = Request.Builder()
                .url("https://login.questrade.com/oauth2/token?grant_type=refresh_token&refresh_token=" + refresh_token).build()

        //https://reqres.in/api/users?page=2
        client.newCall(request).enqueue(object : Callback {
            //cannot not make http requests on mainthread
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (response.isSuccessful) {
                        val myResponse = response.body
                        val jsonstr = myResponse?.string() ?: "NULL"



                        this@MainActivity.runOnUiThread(object : Runnable {
                            override fun run() {
                                texter.text = jsonstr
                            }

                        })
                    }
                }
            }
        })

    }


}