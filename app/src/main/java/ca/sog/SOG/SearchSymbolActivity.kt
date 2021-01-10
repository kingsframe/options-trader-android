package ca.sog.SOG

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast

class SearchSymbolActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_symbol)

        if (Intent.ACTION_SEARCH == intent.action) {        // Receiving teh query
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                Toast.makeText(this, "test3", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {        // Receiving teh query
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                Toast.makeText(this, "test3", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu?.findItem(R.id.actionSearch)          //unsafe call
        val searchView = searchItem?.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                Toast.makeText(this@SearchSymbolActivity, "test1",Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                Toast.makeText(this@SearchSymbolActivity, "test2",Toast.LENGTH_SHORT).show()
                //want to go to a new activity, We can consult later about simply not requiring a new activity for the search, since it is done on menu bar
                return false
            }
        })
        return true
    }
}
