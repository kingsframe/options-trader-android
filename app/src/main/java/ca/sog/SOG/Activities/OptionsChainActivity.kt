package ca.sog.SOG.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ca.sog.SOG.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_options_chain.*

class OptionsChainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options_chain)

        //TODO parcelize ChainPerRoot
        //val optionChain = intent.extras?.getParcelable("optionChain")
        //Toast.makeText(applicationContext, optionChain[0].optionRoot, Toast.LENGTH_SHORT).show()

        optionType.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{

            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Handle tab select
                Toast.makeText(applicationContext, tab?.text, Toast.LENGTH_SHORT).show()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
                Toast.makeText(applicationContext, tab?.text, Toast.LENGTH_SHORT).show()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Toast.makeText(applicationContext, tab?.text, Toast.LENGTH_SHORT).show()
                // Handle tab unselect
            }

        })

    }
}