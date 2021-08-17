package com.example.launchamap

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showMap(view: View) {
        //Get the values from the UI
        val longitude = findViewById<EditText>(R.id.lngEditText)
        val latitude = findViewById<EditText>(R.id.latEditText)

        //Convert the values to doubles
        val latitudeDouble = latitude.text.toString().toDouble()
        val longitudeDouble = longitude.text.toString().toDouble()

        //Create location and build the intent
        val location = Uri.parse("geo:$latitudeDouble,$longitudeDouble")
        val mapIntent = Intent(Intent.ACTION_VIEW, location)

        //Try to invoke the intent
        try {
            startActivity(mapIntent)
        } catch (e: ActivityNotFoundException) {
            // Define what your app should do if no activity can handle the intent.
            Toast.makeText(this, "There is no activity to handle map intent!", Toast.LENGTH_LONG).show()
        }
    }
}