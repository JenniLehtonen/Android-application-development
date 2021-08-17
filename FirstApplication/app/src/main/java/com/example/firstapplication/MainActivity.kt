package com.example.firstapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun buttonClicked(view: View){
        val helloTextView = findViewById<TextView>(R.id.helloTextView)
        helloTextView.text = resources.getText(R.string.button_clicked_text)
        Log.d("FirstApp", "Button is Clicked!")
    }


}