package ca.sog.SOG

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import com.google.gson.GsonBuilder
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class PositionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_position)

//        val tokenBundle = intent.extras
//        val tokensList = tokenBundle?.getStringArrayList("tokens") ?: ArrayList<String>()

    }

    inner class OptionChain {} //TODO

    fun searchRes (tokensList : ArrayList<String>, symbolStr : String) : OptionChain{
        val access_token = tokensList[0]
        val refresh_token = tokensList[1]
        val token_type = tokensList[2]
        val expires_in = tokensList[3]
        val api_server = tokensList[4]

//        GET /v1/accounts HTTP/1.1
//        Host: https://api01.iq.questrade.com
//        Authorization: Bearer C3lTUKuNQrAAmSD/TPjuV/HI7aNrAwDp
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(api_server + "v1/symbols/9291/options")
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
//                        val accountsJSONlist = responseBodyJson.getJSONArray("accounts") //User may have more than 1 account! want to select them all here
//                        val gson = GsonBuilder().create()
//                        val responseAccountsArray = gson.fromJson(accountsJSONlist.toString(), Array<QuestAccount>::class.java)
//                        responseAccountsList.addAll(responseAccountsArray.toCollection(mutableListOf()))
//
//                        this@PositionActivity.runOnUiThread(kotlinx.coroutines.Runnable { accountsAdapter.notifyDataSetChanged() })
                    }
                }
            }
        })
        return OptionChain()
    }


//    override fun onNewIntent(intent: Intent) {
//        super.onNewIntent(intent)
//        handleIntent(intent)
//    }
//
//    private fun handleIntent(intent: Intent) {
//        if (Intent.ACTION_SEARCH == intent.action) {        // Receiving teh query
//            val searchQuery: String = intent.getStringExtra(SearchManager.QUERY) ?: "" //Gives empty str if null call
//            Toast.makeText(this, searchQuery, Toast.LENGTH_SHORT).show()
//
////            val tokenBundle = intent.extras
////            val tokensList = tokenBundle?.getStringArrayList("tokens") ?: ArrayList<String>()
////            val optChain = searchRes(tokensList, "")
//
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.actionSearch)          //unsafe call (?)
        val searchView = searchItem?.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setSubmitButtonEnabled(true)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                //Toast.makeText(this@SearchSymbolActivity, "test1",Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                Toast.makeText(this@PositionActivity, "test2", Toast.LENGTH_SHORT).show()
                //want to go to a new activity, We can consult later about simply not requiring a new activity for the search, since it is done on menu bar

                val tokenBundle = intent.extras
                val tokensList = tokenBundle?.getStringArrayList("tokens") ?: ArrayList<String>()
                val optChain = searchRes(tokensList, p0 ?: "")

                return false
            }
        })
        return true
    }
}