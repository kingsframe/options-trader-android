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
import kotlinx.android.synthetic.main.activity_accounts.*
import kotlinx.android.synthetic.main.activity_ticker_search.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class TickerSearchActivity : AppCompatActivity(), OnItemClickListener{

    //init{}
    lateinit var responseList : MutableList<Ticker>
    lateinit var tickerAdapter : TickerAdapter

    override fun onItemClicked(symbolId: Int) {
        val intent = Intent(this, TickerSearchActivity::class.java)
        intent.putExtra("symbolId", symbolId)
        startActivity(intent);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticker_search)
        responseList = mutableListOf<Ticker>()
        tickerAdapter = TickerAdapter(responseList, this)
        tickerRecycleView.adapter = tickerAdapter

//        accountsRecycleView.adapter = accountsAdapter //TODO recyclerview

//        val intent = Intent(this, OptionsSearchActivity::class.java) //TODO test
//        val tokenBundle = Bundle()
//        tokenBundle.putString("symbol", "TSM")
//        tokenBundle.putString("symbolId", "38052")
//        intent.putExtras(tokenBundle)
//        startActivity(intent);
    }


    fun searchRes(tokensList : ArrayList<String>, query : String){
        val access_token = tokensList[0]
        val refresh_token = tokensList[1]
        val token_type = tokensList[2]
        val expires_in = tokensList[3]
        val api_server = tokensList[4]

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
                        responseList.addAll(tickersArray.toCollection(mutableListOf()))
                        this@TickerSearchActivity.runOnUiThread(kotlinx.coroutines.Runnable { tickerAdapter.notifyDataSetChanged() }) //update the recyclerView
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

            override fun onQueryTextSubmit(query: String?): Boolean { //TODO query is the input str
                //want to go to a new activity, We can consult later about simply not requiring a new activity for the search, since it is done on menu bar
                val tokenBundle = intent.extras
                val tokensList = tokenBundle?.getStringArrayList("tokens") ?: ArrayList<String>()
                searchRes(tokensList, query ?: "") //grab results in this.responseList //TODO race condition
                Toast.makeText(getApplicationContext(),"test",Toast.LENGTH_SHORT).show();
//                TODO update option chain list and display recycle view
                return true
            }
        })
        return true
    }
}