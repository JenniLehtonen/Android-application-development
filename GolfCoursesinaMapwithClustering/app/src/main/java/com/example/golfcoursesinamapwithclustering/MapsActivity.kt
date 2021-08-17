package com.example.golfcoursesinamapwithclustering

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.JsonObjectRequest

import com.google.android.gms.maps.GoogleMap
import com.example.golfcoursesinamapwithclustering.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import org.json.JSONArray

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, ClusterManager.OnClusterItemClickListener<GolfCourseItem> {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    //Declare a variable for the cluster manager
    private lateinit var clusterManager: ClusterManager<GolfCourseItem>

    private lateinit var markerClusterRenderer: MarkerClusterRenderer
    var clickedItem: GolfCourseItem? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    internal inner class CustomInfoWindowAdapter : GoogleMap.InfoWindowAdapter {
        private val contents: View = layoutInflater.inflate(R.layout.info_window, null)

        override fun getInfoWindow(marker: Marker?): View? {
            return null
        }

        override fun getInfoContents(marker: Marker): View {
            // UI elements
            val titleTextView = contents.findViewById<TextView>(R.id.titleTextView)
            val addressTextView = contents.findViewById<TextView>(R.id.addressTextView)
            val phoneTextView = contents.findViewById<TextView>(R.id.phoneTextView)
            val emailTextView = contents.findViewById<TextView>(R.id.emailTextView)
            val webTextView = contents.findViewById<TextView>(R.id.webTextView)

            //Get data from Tag
            val list: List<String> = clickedItem?.getTag() as List<String>
            titleTextView.text = list[0]
            addressTextView.text = list[1]
            phoneTextView.text = list[2]
            emailTextView.text = list[3]
            webTextView.text = list[4]

            return contents
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        clusterManager = ClusterManager<GolfCourseItem>(this, mMap)

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(clusterManager)
        mMap.setOnMarkerClickListener(clusterManager)

        markerClusterRenderer = MarkerClusterRenderer(this, mMap, clusterManager)
        clusterManager.renderer = markerClusterRenderer
        clusterManager.setOnClusterItemClickListener(this)
        clusterManager.markerCollection.setInfoWindowAdapter(CustomInfoWindowAdapter())
        clusterManager.cluster()
        loadData()
        mMap.uiSettings.isZoomControlsEnabled = true
    }

    private fun loadData() {
        val queue = Volley.newRequestQueue(this)
        // 1. code here
        val url = "https://ptm.fi/materials/golfcourses/golf_courses.json"
        var golfCourses: JSONArray
        val courseTypes: Map<String, Float> = mapOf(
            "?" to BitmapDescriptorFactory.HUE_VIOLET,
            "Etu" to BitmapDescriptorFactory.HUE_BLUE,
            "Kulta" to BitmapDescriptorFactory.HUE_GREEN,
            "Kulta/Etu" to BitmapDescriptorFactory.HUE_YELLOW
        )

        // create JSON request object
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                // JSON loaded successfully
                // 2. code here
                golfCourses = response.getJSONArray("courses")
                // loop through all objects
                for (i in 0 until golfCourses.length()){
                    // get course data
                    val course = golfCourses.getJSONObject(i)
                    val lat = course["lat"].toString().toDouble()
                    val lng = course["lng"].toString().toDouble()
                    val type = course["type"].toString()
                    val title = course["course"].toString()
                    val address = course["address"].toString()
                    val phone = course["phone"].toString()
                    val email = course["email"].toString()
                    val webUrl = course["web"].toString()

                    val list = listOf(title, address, phone, email, webUrl)

                    if (courseTypes.containsKey(type)) {
                        val item = GolfCourseItem(
                            lat,
                            lng,
                            type,
                            list
                        )
                        //Add cluster item
                        clusterManager.addItem(item)
                    } else {
                        Log.d("GolfCourses", "This course type does not exist in evaluation $type")
                    }
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(65.5, 26.0),5.0F))
            },
            { error ->
                // Error loading JSON
                Log.d("Problem in JSON", "Something went wrong")
            }
        )
        // Add the request to the RequestQueue
        queue.add(jsonObjectRequest)

    }

    override fun onClusterItemClick(item: GolfCourseItem?): Boolean {
        clickedItem = item
        return false
    }
}

class GolfCourseItem(lat: Double, lng: Double, private val type: String, private val tag: List<String>) : ClusterItem {
    private val position: LatLng = LatLng(lat, lng)
    private val snippet: String = ""

    override fun getSnippet(): String {
        return snippet
    }

    override fun getTitle(): String {
        return tag[0]
    }

    override fun getPosition(): LatLng {
        return position
    }

    fun getType(): String {
        return type
    }

    fun getTag(): List<String> {
        return tag
    }

}

class MarkerClusterRenderer(context: Context?, map: GoogleMap?, clusterManager: ClusterManager<GolfCourseItem>?) :
    DefaultClusterRenderer<GolfCourseItem>(context, map, clusterManager) {

    override fun onBeforeClusterItemRendered(
        item: GolfCourseItem,
        markerOptions: MarkerOptions
    ) {
        val courseTypes: Map<String, Float> = mapOf(
            "?" to BitmapDescriptorFactory.HUE_VIOLET,
            "Etu" to BitmapDescriptorFactory.HUE_BLUE,
            "Kulta" to BitmapDescriptorFactory.HUE_GREEN,
            "Kulta/Etu" to BitmapDescriptorFactory.HUE_YELLOW
        )
        val iconColor = courseTypes[item.getType()]
        markerOptions.icon((iconColor?.let { BitmapDescriptorFactory.defaultMarker(it) }))
    }
}