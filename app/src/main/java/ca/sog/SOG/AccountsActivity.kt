package ca.sog.SOG

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.gson.GsonBuilder
import okhttp3.*
import okio.IOException
import kotlinx.android.synthetic.main.activity_accounts.*
import org.json.JSONObject
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.mutableListOf as mutableListOf

class AccountsActivity : AppCompatActivity(), OnItemClickListener {

    override fun onItemClicked(accountNumber: String) {
        val intent = Intent(this, TickerOptionsActivity::class.java)
        intent.putExtra("accountNumber", accountNumber)
        startActivity(intent);
    }

    private fun onFabClicked() {
        val intent = Intent(this, TickerOptionsActivity::class.java)
        startActivity(intent);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accounts)
        setSupportActionBar(findViewById(R.id.toolbar))

        val fab: View = findViewById(R.id.fabSearch)
        fab.setOnClickListener {
            onFabClicked()
        }

        val responseAccountsList = mutableListOf<QuestAccount>()
        val accountsAdapter = AccountAdapter(responseAccountsList, this)
        accountsRecycleView.adapter = accountsAdapter

        val tokenBundle = intent.extras
        val tokensList = tokenBundle?.getStringArrayList("tokens") ?: ArrayList<String>()
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
                .url(api_server + "v1/accounts")
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
                        val accountsJSONlist = responseBodyJson.getJSONArray("accounts") //User may have more than 1 account! want to select them all here
                        val gson = GsonBuilder().create()
                        val responseAccountsArray = gson.fromJson(accountsJSONlist.toString(), Array<QuestAccount>::class.java)
                        responseAccountsList.addAll(responseAccountsArray.toCollection(mutableListOf()))

                        this@AccountsActivity.runOnUiThread(Runnable { accountsAdapter.notifyDataSetChanged() })
                    }
                }
            }
        })
    }
}