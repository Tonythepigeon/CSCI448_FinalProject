package com.csci448.pathmapper.util

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import com.csci448.pathmapper.R
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.lifecycle.ViewModelProvider
import com.csci448.pathmapper.util.GeoLocatrViewModel
import com.csci448.pathmapper.util.GeoLocatrViewModelFactory
import com.google.android.gms.location.*
import java.io.IOException
import java.security.AccessController.getContext
import kotlin.text.StringBuilder

class LocationUtility(activity: ComponentActivity) {

    val viewModel: GeoLocatrViewModel
    private val fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
    private val locationRequest = LocationRequest.create().apply {
        interval = 0
        numUpdates = 1
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    init {
        val factory = GeoLocatrViewModelFactory()
        viewModel = ViewModelProvider(activity, factory)[factory.getViewModelClass()]
    }
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            Log.d(LOG_TAG, "Got a location: ${locationResult.lastLocation}")
            viewModel.currentLocationLiveData.value = locationResult.lastLocation
        }
    }
    private val permissionLauncher: ActivityResultLauncher<Array<String>> =
        activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        { isGrantedMap ->
            var hasEnoughPermission: Boolean = false
            isGrantedMap.forEach { (perm, isGranted) -> hasEnoughPermission = hasEnoughPermission || isGranted }
            getLocation()
        }
    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
            locationCallback, Looper.getMainLooper())
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun checkPermissionAndGetLocation(activity: ComponentActivity) {
        if (activity.checkSelfPermission( ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED ||
            activity.checkSelfPermission( ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {
            getLocation()
        }else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, ACCESS_FINE_LOCATION)) {
            Toast
                .makeText(activity, R.string.location_reason_message, Toast.LENGTH_SHORT)
                .show()
            Toast
                .makeText(activity, R.string.location_denied, Toast.LENGTH_SHORT)
                .show()
        }else {
            permissionLauncher.launch( arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION) )
        }
    }
    private fun getAddress(context: Context, location: Location?): String {
        if(location == null) return ""
        val geocoder = Geocoder(context)
        val addressTextBuilder = StringBuilder()
        try {
            val addresses = geocoder.getFromLocation(location.latitude,
                location.longitude,
                1)
            if(addresses != null && addresses.isNotEmpty()) {
                val address = addresses[0]
                for(i in 0..address.maxAddressLineIndex) {
                    if(i > 0) {
                        addressTextBuilder.append( "\n" )
                    }
                    addressTextBuilder.append( address.getAddressLine(i) )
                }
            }
        } catch (e: IOException) {
            Log.e(LOG_TAG, "Error getting address: ${e.localizedMessage}")
        }
        return addressTextBuilder.toString()
    }
//    fun getAddressForCurrentLocation(context: Context) {
//        viewModel.currentAddressLiveData.value =
//            getAddress(context, viewModel.currentLocationLiveData.value)
//    }
    fun removeLocationRequest() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}