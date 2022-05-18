package com.example.dicodingstory.presentation.intent

import android.Manifest
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.example.dicodingstory.R
import com.example.dicodingstory.databinding.ActivityMapsBinding
import com.example.dicodingstory.presentation.factory.ViewModelFactory
import com.example.dicodingstory.presentation.viewmodel.StoryViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

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

        title = "Story Maps"


    }

    @OptIn(ExperimentalPagingApi::class)
    override fun onMapReady(googleMap: GoogleMap) {

        val storyViewModel = ViewModelProvider(this,
            ViewModelFactory(this)
        )[StoryViewModel::class.java]

        mMap = googleMap

        setMapStyle()

        var valLat: Double = 0.0
        var valLon : Double = 0.0
        var nLatLon : Double = 0.0

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        lifecycleScope.launch {

            storyViewModel.getData().forEach {
                if(it.lat?.isNaN() == false && it.lon?.isNaN() == false )
                {
                    val locationStory = LatLng(it.lat.toDouble(), it.lon.toDouble())
                    mMap.addMarker(MarkerOptions()
                        .position(locationStory)
                        .title(it.name)
                        .snippet(it.description))

                    valLat += it.lat.toDouble()
                    valLon += it.lon.toDouble()
                    nLatLon += 1

                }

            }
            val centerLocation = LatLng( (valLat/nLatLon), (valLon/nLatLon))
            getMyLocation()

            mMap.moveCamera(CameraUpdateFactory.newLatLng(centerLocation))
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_map))
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", exception)
        }
    }
}