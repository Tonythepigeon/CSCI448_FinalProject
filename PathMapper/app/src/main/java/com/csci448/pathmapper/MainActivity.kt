package com.csci448.pathmapper

import android.app.Activity
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.PackageManagerCompat
import androidx.core.content.PackageManagerCompat.LOG_TAG
import com.csci448.pathmapper.ui.navigation.PathMapperNavHost

import com.csci448.pathmapper.util.LocationUtility
import com.csci448.pathmapper.ui.theme.PathMapperTheme
import com.csci448.pathmapper.util.PolyActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.compose.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


public class MainActivity : ComponentActivity() {
    companion object {
        lateinit var locationUtility: LocationUtility
        val polyActivity = PolyActivity()
        val points = mutableListOf<LatLng>()

        @RequiresApi(Build.VERSION_CODES.M)
        fun locationLogger(start: Boolean, ticks: Long, comp : ComponentActivity ) = runBlocking {
//            launch {
//                while (start) {
//                    delay(ticks)
//                    locationUtility.checkPermissionAndGetLocation(comp)
//                    Log.e(LOG_TAG, "Location logged!")
//                }
//            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationUtility = LocationUtility(this )
        locationUtility.checkPermissionAndGetLocation(this)

        setContent {
            MainActivityContent()
//
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    @Preview(showSystemUi = true, showBackground = true)
    @Composable
    private fun PreviewMainActivity(){
        MainActivityContent()
    }
//    override fun onStop(){
//        super.onStop()
//        locationUtility.removeLocationRequest()
//    }
    @RequiresApi(Build.VERSION_CODES.M)
    @Composable
    fun MainActivityContent(){
        PathMapperTheme() {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                PathMapperNavHost(this)
            }
        }
    }
}




@Composable
fun googleMap(locationState: State<Location?>, onGetLocation: () -> Unit, addressState: State<String>, cameraPositionState: CameraPositionState){
    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()
        .wrapContentWidth(Alignment.CenterHorizontally)){

        val locationPosition = locationState.value?.let {
            LatLng(it.latitude, it.longitude)
        } ?: LatLng(0.0, 0.0)
        val map = GoogleMap(modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            if(locationState.value != null) {
                MainActivity.points.add(locationPosition)
                Marker(
                    position = locationPosition,
                    title = addressState.value,
                    snippet = locationState.value?.latitude.toString() + " / " + locationState.value?.longitude.toString()
                )
                Polyline(points = MainActivity.points)
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
        if (locationPosition != null) {
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
        onGetLocation = { locationUtility.checkPermissionAndGetLocation(comp) },
        cameraPositionState = cameraPositionState
    )
}