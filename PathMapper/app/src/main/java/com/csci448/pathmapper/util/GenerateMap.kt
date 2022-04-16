package com.csci448.pathmapper.util

import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.PackageManagerCompat
import com.csci448.pathmapper.MainActivity
import com.csci448.pathmapper.MainActivityContent
import com.csci448.pathmapper.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import java.security.AccessController
import java.security.AccessController.getContext

class GenerateMap : ComponentActivity() {
    val context = getContext();

    companion object {
        lateinit var locationUtility: LocationUtility
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapContent()
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    @Composable
    fun MapContent(){
        Log.d(PackageManagerCompat.LOG_TAG, "########################################")
        locationUtility = LocationUtility(this@GenerateMap )
        locationUtility.checkPermissionAndGetLocation(this@GenerateMap)
        InteractiveMap(locationUtility = locationUtility, comp = this@GenerateMap)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    @Composable
    fun InteractiveMap(locationUtility : LocationUtility, comp : ComponentActivity) {
        val locationState =
            locationUtility.viewModel.currentLocationLiveData.observeAsState()
        val addressState =
            locationUtility.viewModel.currentAddressLiveData.observeAsState("")
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 0f)
        }
        val con = LocalContext.current
        LaunchedEffect(locationState.value) {
            //locationUtility.getAddressForCurrentLocation( this@MainActivity )
            // create a point for the corresponding lat/long
            val locationPosition = locationState.value?.let {
                LatLng(it.latitude, it.longitude)
            }
            if(locationPosition != null) {
                // include all points that should be within the bounds of the zoom
                // convex hull
                val bounds = LatLngBounds.Builder()
                    .include(locationPosition)
                    .build()
                // add padding
                val padding = con
                    .resources
                    .getDimensionPixelSize(R.dimen.map_inset_padding)
                // create a camera to smoothly move the map view
                val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
                // move our camera!
                cameraPositionState.animate(cameraUpdate)
            }
        }
        googleMap(
            locationState = locationState,
            addressState = addressState,
            onGetLocation = { locationUtility.checkPermissionAndGetLocation(comp)},
            cameraPositionState = cameraPositionState
        )
    }
}
@Composable
fun googleMap(locationState: State<Location?>, onGetLocation: () -> Unit, addressState: State<String>, cameraPositionState: CameraPositionState){
    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()
        .wrapContentWidth(Alignment.CenterHorizontally)){
        Button(enabled = true, onClick = onGetLocation){
            Text("Get Current Location")
        }
        val locationPosition = locationState.value?.let {
            LatLng(it.latitude, it.longitude)
        } ?: LatLng(0.0, 0.0)
        GoogleMap(modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            if(locationState.value != null) {
                Marker(
                    position = locationPosition,
                    title = addressState.value,
                    snippet = locationState.value?.latitude.toString() + " / " + locationState.value?.longitude.toString()
                )
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun InteractiveMap(locationUtility : LocationUtility, comp : ComponentActivity) {
    val locationState =
        locationUtility.viewModel.currentLocationLiveData.observeAsState()
    val addressState =
        locationUtility.viewModel.currentAddressLiveData.observeAsState("")
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 0f)
    }
    val con = LocalContext.current
    LaunchedEffect(locationState.value) {
//        locationUtility.getAddressForCurrentLocation( MainActivity() )
        // create a point for the corresponding lat/long
        val locationPosition = locationState.value?.let {
            LatLng(it.latitude, it.longitude)
        }
        if(locationPosition != null) {
            // include all points that should be within the bounds of the zoom
            // convex hull
            val bounds = LatLngBounds.Builder()
                .include(locationPosition)
                .build()
            // add padding
            val padding = con
                .resources
                .getDimensionPixelSize(R.dimen.map_inset_padding)
            // create a camera to smoothly move the map view
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
            // move our camera!
            cameraPositionState.animate(cameraUpdate)
        }
    }
    googleMap(
        locationState = locationState,
        addressState = addressState,
        onGetLocation = { locationUtility.checkPermissionAndGetLocation(comp)},
        cameraPositionState = cameraPositionState
    )

    @RequiresApi(Build.VERSION_CODES.M)
    @Composable
    fun InteractiveMap(locationUtility : LocationUtility, comp : ComponentActivity) {
        val locationState =
            locationUtility.viewModel.currentLocationLiveData.observeAsState()
        val addressState =
            locationUtility.viewModel.currentAddressLiveData.observeAsState("")
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 0f)
        }
        val con = LocalContext.current
        LaunchedEffect(locationState.value) {
            //locationUtility.getAddressForCurrentLocation( this@MainActivity )
            // create a point for the corresponding lat/long
            val locationPosition = locationState.value?.let {
                LatLng(it.latitude, it.longitude)
            }
            if(locationPosition != null) {
                // include all points that should be within the bounds of the zoom
                // convex hull
                val bounds = LatLngBounds.Builder()
                    .include(locationPosition)
                    .build()
                // add padding
                val padding = con
                    .resources
                    .getDimensionPixelSize(R.dimen.map_inset_padding)
                // create a camera to smoothly move the map view
                val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
                // move our camera!
                cameraPositionState.animate(cameraUpdate)
            }
        }
        googleMap(
            locationState = locationState,
            addressState = addressState,
            onGetLocation = { locationUtility.checkPermissionAndGetLocation(comp)},
            cameraPositionState = cameraPositionState
        )
    }
}