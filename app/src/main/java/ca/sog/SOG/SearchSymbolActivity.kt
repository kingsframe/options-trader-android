package ca.sog.SOG

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast

class SearchSymbolActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_symbol)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu?.findItem(R.id.actionSearch)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                Toast.makeText(this@SearchSymbolActivity, "test1",Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                Toast.makeText(this@SearchSymbolActivity, "test2",Toast.LENGTH_SHORT).show()
                return false
            }
        })
        return true
    }
}