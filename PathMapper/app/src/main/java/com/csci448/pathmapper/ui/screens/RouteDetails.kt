package com.csci448.pathmapper.ui.screens

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.csci448.pathmapper.InteractiveMap
import com.csci448.pathmapper.MainActivity
import com.csci448.pathmapper.R
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions


//@RequiresApi(Build.VERSION_CODES.M)
//@Preview(showBackground = true)
//@Composable
//private fun PreviewRouteDetailsScreen(){
//    RouteDetailsScreen(navController = rememberNavController())
//}


@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun RouteDetailsScreen(navController: NavController, mainActivity : ComponentActivity){
    Column(modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 20.dp)){
        Row {
            Button(
                modifier = Modifier.weight(.2F),
                enabled = true,
                onClick = {navController.navigate("past_routes_screen")}
            ){
                Text("<", textAlign = TextAlign.Center)
            }
            Text("*Route name", color= Color.Blue, fontSize = 32.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(.8F))
        }

        InteractiveMap(locationUtility = MainActivity.locationUtility , comp = mainActivity)


        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.info_label), textAlign = TextAlign.Center, fontSize = FONT_SIZE)
        Spacer(modifier = Modifier.height(16.dp))
        Row() {
            Text(stringResource(R.string.date_label),
                Modifier.weight(0.5F), textAlign = TextAlign.Start, fontSize = FONT_SIZE)
            Text(stringResource(R.string.time_label),
                Modifier.weight(0.5F), textAlign = TextAlign.End, fontSize = FONT_SIZE)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row() {
            Text(stringResource(R.string.length_label),
                Modifier.weight(0.5F), textAlign = TextAlign.Start, fontSize = FONT_SIZE)
            Text(stringResource(R.string.average_speed_label),
                Modifier.weight(0.5F), textAlign = TextAlign.End, fontSize = FONT_SIZE)
        }
    }
}
