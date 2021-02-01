package ca.sog.SOG

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_ticker_search.*

class OptionsChainActivity: AppCompatActivity(), OnItemClickListener{
    lateinit var responseOptionsList : MutableList<Options>
    //lateinit var optionsAdapter = OptionsAdapter

    val symbolInfo = intent.extras
    val symbolId = symbolInfo?.getString("symbolId") ?: String()
    val symbolName = symbolInfo?.getString("symbolName") ?: String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "Showing results for ${symbolName}", Toast.LENGTH_LONG).show()
    }

    override fun onItemClicked(symbolId: Int, symbolName: String) {
        TODO("Not yet implemented")
    }


    //GET https://api01.iq.questrade.com/v1/symbols/9291/options

}