package ca.sog.SOG

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class PositionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_positions)
        setSupportActionBar(findViewById(R.id.toolbar))

        val accountNumber = intent.getStringExtra("accountNumber")

        Toast.makeText(this,"account number ${accountNumber}",Toast.LENGTH_LONG).show()

    }
}