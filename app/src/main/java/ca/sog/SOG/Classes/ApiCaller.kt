package ca.sog.SOG.Classes

import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class ApiCaller(path: String, jsonName: String) {
    var access_token : String = Tokens.accessToken
    var api_server : String = Tokens.apiServer
    var responseJson: JSONObject? = null
    lateinit var responseBodyJson : JSONObject
    lateinit var jsonList: JSONArray

    init {
        makeApiCall(path, jsonName)
    }

    fun makeApiCall(path: String, jsonName: String): JSONArray {
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
                        jsonList = responseBodyJson.getJSONArray(jsonName)
                    }
                }
            }
        })
        return jsonList
    }
}



