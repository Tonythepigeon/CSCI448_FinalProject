package com.csci448.pathmapper.util

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
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
import androidx.navigation.NavController
import com.csci448.capra_a3.ui.viewmodels.IViewModel
import com.csci448.pathmapper.MainActivity
import com.csci448.pathmapper.MainActivity.Companion.thisViewModel
import com.csci448.pathmapper.R
import com.csci448.pathmapper.data.database.Path
import com.google.android.gms.location.*
import java.io.IOException
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class LocationUtility(activity: ComponentActivity) {

    val viewModel: IViewModel = MainActivity.thisViewModel
    private val fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
    private val locationRequest = LocationRequest.create().apply {
        interval = 10000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    init {

//        val factory = GeoLocatrViewModelFactory()
//        viewModel = ViewModelProvider(activity, factory)[factory.getViewModelClass()]
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
            getLocation(pass = 0)
        }
    @SuppressLint("MissingPermission")
    private fun getLocation(pass: Int) {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
            locationCallback, Looper.getMainLooper())
    }
    private fun distance(lat1: Double?, lon1: Double?, lat2: Double?, lon2: Double?): String {
        if(lat1 != null && lat2 != null && lon1 != null && lon2 != null) {
            val theta = lon1 - lon2
            var dist = (Math.sin(deg2rad(lat1))
                    * Math.sin(deg2rad(lat2))
                    + (Math.cos(deg2rad(lat1))
                    * Math.cos(deg2rad(lat2))
                    * Math.cos(deg2rad(theta))))
            dist = Math.acos(dist)
            dist = rad2deg(dist)
            dist = dist * 60 * 1.1515
            val df = DecimalFormat("#.#")
            return df.format(dist * 5280) //Conversion to feet
        }
        return "0.0"
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }
    fun logLocation(pass: Int, navController: NavController){
        locationRequest.interval = MainActivity.thisViewModel.thisPowerLevel.toLong()
        val day = SimpleDateFormat("dd/M/yyyy")
        val time =  SimpleDateFormat("hh:mm:ss")
        if(pass == 1){
            Log.e(LOG_TAG, "PASS 1 WENT THROUGH with lat: " + MainActivity.locationState.value?.latitude)
            Log.e(LOG_TAG, "PASS 1 WENT THROUGH with long: " + MainActivity.locationState.value?.longitude)
            val currentDay = day.format(Date())
            val currentTime = time.format(Date())
            MainActivity.thisViewModel.thisPath = (Path(
                date = currentDay,
                length = "0",
                startLat = MainActivity.locationState.value?.latitude,
                endLat = 1.1,
                startLng = MainActivity.locationState.value?.longitude,
                endLng = 1.1,
                startTime = currentTime,
                endTime = "",
                color = MainActivity.thisViewModel.thisPath?.color ?: ""
            ))
            if(MainActivity.thisViewModel.thisPath == null){
                Log.e(LOG_TAG, "thisPath is NULL somehow?")
            }
        }else if(pass == 2){
            val currentTime = time.format(Date())

            val oldPath = MainActivity.thisViewModel.thisPath
            if(oldPath != null) {
                //locationRequest.setNumUpdates(1)
                Log.e(LOG_TAG, "PASS 2 WENT THROUGH with lats: " + oldPath.startLat + " " + MainActivity.locationState.value?.latitude)
                Log.e(LOG_TAG, "PASS 2 WENT THROUGH with lngs: " + oldPath.startLng + " " + MainActivity.locationState.value?.longitude)
                //###USE REAL DATA VS SIMULATED###
//                MainActivity.thisViewModel.currentLocationLiveData.value?.latitude = MainActivity.thisViewModel.currentLocationLiveData.value?.latitude?.plus(
//                    0.00005
//                )!!
//                MainActivity.thisViewModel.currentLocationLiveData.value?.longitude = MainActivity.thisViewModel.currentLocationLiveData.value?.longitude?.plus(
//                    0.00008
//                )!!
                var totalDistace : Double = 0.0
                var i : Int = 0
                val pathList = thisViewModel.thisPassData.toList()
                if(pathList != null) {
                    while (i < pathList.size - 1) {
                        totalDistace += distance(
                            pathList[i].latitude,
                            pathList[i].longitude,
                            pathList[i + 1].latitude,
                            pathList[i + 1].longitude
                        ).toDouble()
                        i++
                    }
                }
                var df = DecimalFormat("#.#")
                MainActivity.thisViewModel.thisPath = (Path(
                    date = oldPath.date,
                    length = df.format(totalDistace),
                    startLat = oldPath.startLat,
                    endLat = MainActivity.locationState.value?.latitude,
                    startLng = oldPath.startLng,
                    endLng = MainActivity.locationState.value?.longitude,
                    startTime = oldPath.startTime,
                    endTime = currentTime,
                    color = MainActivity.thisViewModel.thisPath?.color ?: ""
                ))

                MainActivity.thisViewModel.addPath(
                    MainActivity.thisViewModel.thisPath!!
                )
                navController.navigate("route_details_screen")
            }else{
                Log.e(LOG_TAG, "PASS 2 WENT THROUGH [NULL]")

                MainActivity.thisViewModel.addPath(
                    Path(
                        date = "null",
                        length = "null",
                        startLat = 0.0,
                        endLat = MainActivity.thisViewModel.currentLocationLiveData.value?.latitude,
                        startLng = 0.0,
                        endLng = MainActivity.thisViewModel.currentLocationLiveData.value?.longitude,
                        startTime = "null",
                        endTime = currentTime,
                        color = MainActivity.thisViewModel.thisPath?.color ?: ""
                    )
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkPermissionAndGetLocation(activity: ComponentActivity, pass: Int) {
        if (activity.checkSelfPermission( ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED ||
            activity.checkSelfPermission( ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {
            Log.e(LOG_TAG, "Permission granted! Getting location with pass: " + pass)
            getLocation(pass)
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