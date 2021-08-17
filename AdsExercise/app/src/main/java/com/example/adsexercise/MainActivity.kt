package com.example.adsexercise

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*

class MainActivity : AppCompatActivity() {

    //For the banner ad
    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Banner ad -->
        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        fun checkCredentials() {
            var username = findViewById<EditText>(R.id.username)
            var password = findViewById<EditText>(R.id.password)

            //Check if username or password is empty. If they aren't empty, log in.
            if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
                Toast.makeText(applicationContext,"Couldn't log in!",Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(applicationContext,"Login succeeded, welcome to Traveling World!",Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SecondPage::class.java)
                startActivity(intent)
            }
        }

        //When log in button is clicked
        val button = findViewById<Button>(R.id.loginButton)
        button.setOnClickListener {
            checkCredentials()
        }
    }
}
