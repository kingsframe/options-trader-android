package ca.sog.SOG.Activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.Menu
import android.widget.SearchView
import ca.sog.SOG.*
import ca.sog.SOG.Classes.SuggestionsDBHandler
import ca.sog.SOG.Classes.TickerSearchSuggestionProvider
import ca.sog.SOG.Classes.Tokens
import ca.sog.SOG.DataClass.Ticker
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_accounts.*
import kotlinx.android.synthetic.main.activity_ticker_search.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class TickerSearchActivity : AppCompatActivity(), OnItemClickListener {
    lateinit var responseList : MutableList<Ticker>
    lateinit var tickerAdapter : TickerAdapter
    //lateinit var tokensList : ArrayList<String>

    override fun onItemClicked(symbolId: Int, symbolName: String) {
        val intent = Intent(this, ExpDateActivity::class.java)
        intent.putExtra("symbolId", symbolId)
        intent.putExtra("symbolName", symbolName)
        //intent.putExtra("tokens", tokensList)
        startActivity(intent);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticker_search)
        //val tokenBundle = intent.extras
        //tokensList = tokenBundle?.getStringArrayList("tokens") ?: ArrayList<String>()
        responseList = mutableListOf<Ticker>()
        tickerAdapter = TickerAdapter(responseList, this)
        tickerRecycleView.adapter = tickerAdapter
        //supportActionBar?.title = "Select Underlying Stock"

        val sugDB = SuggestionsDBHandler(this)
        val recentSuggestion = sugDB.getMostRecentSuggestion()

        if (recentSuggestion != null) {
            //searchRes(tokensList, recentSuggestion)
            searchRes(recentSuggestion)
        }
    }


    fun searchRes(query : String){
        val access_token = Tokens.accessToken
        val api_server = Tokens.apiServer

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(api_server + "v1/symbols/search?prefix=" + query)
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
                        val tickersJsonList = responseBodyJson.getJSONArray("symbols")
                        val gson = GsonBuilder().create()
                        val tickersArray = gson.fromJson(tickersJsonList.toString(), Array<Ticker>::class.java)

                        this@TickerSearchActivity.runOnUiThread(kotlinx.coroutines.Runnable {
                            responseList.addAll(tickersArray.toCollection(mutableListOf()))
                            tickerAdapter.notifyDataSetChanged()
                        }) //update the recyclerView
                        //create option date object and Recycle adapter and then notify createOption changed
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.actionSearch)          //unsafe call (Why?)
        val searchView = searchItem?.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setSubmitButtonEnabled(true)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                responseList.removeAll(responseList)
                searchRes(query ?: "")
                SearchRecentSuggestions(this@TickerSearchActivity, TickerSearchSuggestionProvider.AUTHORITY, TickerSearchSuggestionProvider.MODE).saveRecentQuery(query,null)
                return true
            }
        })
        return true
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (Intent.ACTION_SEARCH == intent?.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                responseList.removeAll(responseList)
                searchRes(query ?: "")
            }
        }
    }
}
