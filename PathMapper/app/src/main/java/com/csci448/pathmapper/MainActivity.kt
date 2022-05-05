package com.csci448.pathmapper

import android.app.Activity
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.PackageManagerCompat
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.lifecycle.ViewModelProvider
import com.csci448.capra_a3.ui.viewmodels.ThisViewModel
import com.csci448.capra_a3.ui.viewmodels.ViewModelFactory
import com.csci448.pathmapper.data.database.Path
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
        lateinit var thisViewModel: ThisViewModel
        lateinit var locationState:  State<Location?>
        val points = mutableListOf<LatLng>()

//        @RequiresApi(Build.VERSION_CODES.M)
//        fun locationLogger(start: Boolean, ticks: Long, comp : ComponentActivity ) = runBlocking {
//            launch {
//                while (start) {
//                    delay(ticks)
//                    locationUtility.checkPermissionAndGetLocation(comp)
//                    Log.e(LOG_TAG, "Location logged!")
//                }
//            }
//        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ViewModelFactory(this)
        thisViewModel = ViewModelProvider(this, factory).get(factory.getViewModelClass())
        locationUtility = LocationUtility(this )

        setContent {
            locationState = MainActivity.thisViewModel.currentLocationLiveData.observeAsState()
            Log.e(LOG_TAG, "DEF PASS -1 WENT THROUGH with lat: " + locationState.value?.latitude)
            if(locationState.value?.latitude != null && locationState.value?.longitude != null) {
                thisViewModel.thisPassData.add(
                    LatLng(
                        locationState.value!!.latitude,
                        locationState.value!!.longitude
                    )
                )
            }
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
fun googleMap(Width: Float, Height: Float, locationState: State<Location?>, onGetLocation: () -> Unit, addressState: State<String>, cameraPositionState: CameraPositionState){
    Column(modifier = Modifier
        .padding(8.dp)
        .wrapContentWidth(Alignment.CenterHorizontally)){

        val locationPosition = locationState.value?.let {
            LatLng(it.latitude, it.longitude)
        } ?: LatLng(0.0, 0.0)
        GoogleMap(modifier = Modifier.fillMaxWidth(Width) .fillMaxHeight(Height),
            cameraPositionState = cameraPositionState,

        ) {
            if(locationState.value != null) {
//                MainActivity.points.add(locationPosition)
//                if(Height != 0.5F) {
//                    Marker(
//                        position = locationPosition,
//                        title = addressState.value,
//                        snippet = locationState.value?.latitude.toString() + " / " + locationState.value?.longitude.toString()
//                    )
//                }
//                if(Height == 0.5F) {
                    val tmp : Path = MainActivity.thisViewModel.thisPath!!
                    Log.e(LOG_TAG, "GENERATE POLYLINE: " + tmp.startLat + " " + tmp.endLat)
//                    val temp : List<LatLng> = listOf(LatLng(tmp.startLat!!, tmp.startLng!!), LatLng(tmp.endLat!!, tmp.endLng!!))

                    Polyline(
                        MainActivity.thisViewModel.thisPassData.toList(),
                        color = Color(MainActivity.thisViewModel.thisPath!!.color.substring(6,9).toFloat(), MainActivity.thisViewModel.thisPath!!.color.substring(11,14).toFloat(), MainActivity.thisViewModel.thisPath!!.color.substring(16,19).toFloat())
                    )
//                }
            }
        }

    }
}
@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun InteractiveMap(width : Float, height : Float, locationUtility : LocationUtility, comp : ComponentActivity) {
    val locationState =
        locationUtility.viewModel.currentLocationLiveData.observeAsState()
    val addressState =
        locationUtility.viewModel.currentAddressLiveData.observeAsState("")
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(
            MainActivity.locationState.value?.latitude ?: 5.0,
            MainActivity.locationState.value?.longitude ?: 5.0),
            0.0f)
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
        Width = width,
        Height = height,
        locationState = locationState,
        addressState = addressState,
        onGetLocation = { locationUtility.checkPermissionAndGetLocation(comp, 0) },
        cameraPositionState = cameraPositionState
    )
}