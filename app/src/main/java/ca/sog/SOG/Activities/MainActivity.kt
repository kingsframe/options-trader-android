package ca.sog.SOG.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import ca.sog.SOG.R

class MainActivity : AppCompatActivity() { //Login Button (all this does)
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginButton = findViewById(R.id.LogInButton)

        loginButton.setOnClickListener{
            // Handler code here.
            val intent = Intent(this, StartLogInActivity::class.java)
            startActivity(intent);
        }
    }

}