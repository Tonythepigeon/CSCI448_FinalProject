package com.csci448.pathmapper

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.csci448.pathmapper.ui.navigation.PathMapperNavHost

import com.csci448.pathmapper.util.LocationUtility
import com.csci448.pathmapper.ui.theme.PathMapperTheme
import com.csci448.pathmapper.util.GenerateMap
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.rememberCameraPositionState


public class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainActivityContent()
//            val temp = GenerateMap()
//            temp.MapContent()
        }
    }
    @Preview(showSystemUi = true, showBackground = true)
    @Composable
    private fun PreviewMainActivity(){
        MainActivityContent()
    }
//    override fun onStop(){
//        super.onStop()
//        locationUtility.removeLocationRequest()
//    }

}



@Composable
fun MainActivityContent(){
    PathMapperTheme() {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            PathMapperNavHost()
        }
    }

}