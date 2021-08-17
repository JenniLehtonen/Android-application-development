package com.example.citymapexercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.citymapexercise.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback{
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in five places
        val maarianhamina = LatLng(60.097101, 19.935432)
        val porvoo = LatLng(60.394197, 25.658631)
        val hanko = LatLng(59.818804, 22.916782)
        val hameenlinna = LatLng(61.001930, 24.460679)
        val rauma = LatLng(61.128007, 21.510838)
        mMap.addMarker(MarkerOptions().position(maarianhamina).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).title("Marker in Maarianhamina").snippet("Population: 11 621"))
        mMap.addMarker(MarkerOptions().position(porvoo).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)).title("Marker in Porvoo").snippet("Population: 50 610"))
        mMap.addMarker(MarkerOptions().position(hanko).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title("Marker in Hanko").snippet("Population: 8 182"))
        mMap.addMarker(MarkerOptions().position(hameenlinna).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title("Marker in Hämeenlinna").snippet("Population: 67 774"))
        mMap.addMarker(MarkerOptions().position(rauma).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).title("Marker in Rauma").snippet("Population: 39 006"))

        //Zooming buttons
        mMap.uiSettings.isZoomControlsEnabled = true
    }
}