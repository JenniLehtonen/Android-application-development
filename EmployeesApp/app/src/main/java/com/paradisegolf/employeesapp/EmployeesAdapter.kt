package com.paradisegolf.employeesapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONArray
import org.json.JSONObject

// Employees Adapter, used in RecyclerView in MainActivity
@Suppress("DEPRECATION")
class EmployeesAdapter(private val employees: JSONArray)
    : RecyclerView.Adapter<EmployeesAdapter.ViewHolder>() {
    // Create UI View Holder from XML layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : EmployeesAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.employee_item, parent, false)
        return ViewHolder(view)
    }

    // View Holder class to hold UI views
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val emailTextView: TextView = view.findViewById(R.id.emailTextView)
        val phoneTextView: TextView = view.findViewById(R.id.phoneTextView)
        val departmentTextView: TextView = view.findViewById(R.id.departmentTextView)
        val imageView: ImageView = view.findViewById(R.id.imageView)

        // add a item click listener
        init {
            itemView.setOnClickListener {
                /*    Toast.makeText(
                    view.context,
                    "Employee name is ${nameTextView.text}, adapter position = $adapterPosition",
                            Toast.LENGTH_LONG).show()
            }*/
                // create an explicit intent
                val intent = Intent(view.context, EmployeeActivity::class.java)
                // add selected employee JSON as a string data
                intent.putExtra("employee", employees[adapterPosition].toString())
                // start a new activity
                view.context.startActivity(intent)
            }
        }
    }

    // Bind data to UI View Holder
    override fun onBindViewHolder(
        holder: EmployeesAdapter.ViewHolder,
        position: Int)
    {
        // employee to bind UI
        val employee: JSONObject = employees.getJSONObject(position)
        // employee lastname and firstname
        // TASK: you can modify this to use formatting strings with placeholders
        holder.nameTextView.text =
            employee["lastName"].toString()+" "+ employee["firstName"].toString()
        holder.titleTextView.text =
            employee["title"].toString()
        holder.emailTextView.text =
            employee["email"].toString()
        holder.phoneTextView.text =
            employee["phone"].toString()
        holder.departmentTextView.text =
            employee["department"].toString()
        Glide.with(holder.imageView.context).load(employee["image"]).circleCrop().into(holder.imageView)
    }

    // Return item count in employees
    override fun getItemCount(): Int = employees.length()
}