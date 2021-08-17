package com.paradisegolf.golfcoursewishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GolfCourseWishlistAdapter(private val placeList: ArrayList<Place>)
    : RecyclerView.Adapter<GolfCourseWishlistAdapter.ViewHolder>()
    {

    // create UI View Holder from XML layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : GolfCourseWishlistAdapter.ViewHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.row_places, parent, false)
        return ViewHolder(view)
    }

    // bind data to UI View Holder
    override fun onBindViewHolder(
        holder: GolfCourseWishlistAdapter.ViewHolder,
        position: Int
    ) {
        // place to bind UI
        val place: Place = placeList[position]
        // name
        holder.nameTextView.text = place.name
        // image
        Glide.with(holder.imageView.context)
            .load(place.getImageResourceId(holder.imageView.context))
            .into(holder.imageView)
    }

    // return item count in employees
    override fun getItemCount(): Int = placeList.size

        // View Holder class to hold UI views
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val nameTextView: TextView = view.findViewById(R.id.placeName)
            val imageView: ImageView = view.findViewById(R.id.placeImage)
        }



}