package ca.sog.SOG.Classes

import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ApiCaller(path : String) {
    var access_token : String = Tokens.accessToken
    var api_server : String = Tokens.apiServer
    lateinit var responseJson: JSONObject

    init {
        makeApiCall(path)
    }

    fun makeApiCall(path : String)  {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(api_server + path)
                .header("Authorization", "Bearer $access_token")
                .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response){
                response.use {
                    if (response.isSuccessful) {
                        val responseBody = response.body
                        val responseBodyString = responseBody?.string() ?: "NULL"
                        responseJson = JSONObject(responseBodyString)
                    }
                }
            }
        })
    }
}



