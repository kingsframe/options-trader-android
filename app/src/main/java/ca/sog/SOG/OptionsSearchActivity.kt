package ca.sog.SOG

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_accounts.*

//TODO we are given in bundle:
//"symbol": "TSM",
//"symbolId": 38052,

class OptionsSearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options_search)

        val symbol = intent.extras?.getString("symbol")
        val symbolId = intent.extras?.getString("symbolId")

        supportActionBar?.title = symbol

    }
}