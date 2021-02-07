package ca.sog.SOG

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_accounts.*
import kotlinx.android.synthetic.main.activity_options_search.*

//TODO we are given in bundle:
//"symbol": "TSM",
//"symbolId": 38052,

class OptionsSearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options_search)

        val symbolId = intent.extras?.getInt("symbolId")
        val symbolName = intent.extras?.getString("symbolName")

        supportActionBar?.title = symbolName
        optionSymbolID.text = symbolId.toString()
    }
}