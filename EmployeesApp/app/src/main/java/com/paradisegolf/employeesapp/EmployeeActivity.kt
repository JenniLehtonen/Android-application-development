package com.paradisegolf.employeesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import org.json.JSONObject

class EmployeeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)

        // get data from intent
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            val employeeString = bundle.getString("employee")
            if (employeeString != null) {
                val employee = JSONObject(employeeString)

                //Find elements from the layout and show the information of the employee
                val imageView: ImageView = findViewById(R.id.ImageView)
                Glide.with(imageView.context).load(employee["image"]).circleCrop().into(imageView)

                val nameTextView: TextView = findViewById(R.id.NameTextView)
                nameTextView.text = employee["lastName"].toString()+" "+ employee["firstName"].toString()

                val titleTextView: TextView = findViewById(R.id.TitleTextView)
                titleTextView.text = employee["title"].toString()

                val emailTextView: TextView = findViewById(R.id.EmailTextView)
                emailTextView.text = employee["email"].toString()

                val phoneTextView: TextView = findViewById(R.id.PhoneTextView)
                phoneTextView.text = employee["phone"].toString()

                val departmentTextView: TextView = findViewById(R.id.DepartmentTextView)
                departmentTextView.text = employee["department"].toString()
            }
        }
    }
}