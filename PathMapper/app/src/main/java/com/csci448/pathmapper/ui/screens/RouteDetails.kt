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
import java.text.DecimalFormat


//@RequiresApi(Build.VERSION_CODES.M)
//@Preview(showBackground = true)
//@Composable
//private fun PreviewRouteDetailsScreen(){
//    RouteDetailsScreen(navController = rememberNavController())
//}


@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun RouteDetailsScreen(navController: NavController, mainActivity : MainActivity){
    Column(modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 20.dp)){
        Row {
            Button(
                modifier = Modifier.weight(.4F),
                enabled = true,
                onClick = {navController.navigate("past_routes_screen")}
            ){
                Text("History", textAlign = TextAlign.Center)
            }
            Text("Route Details", color= Color.Blue, fontSize = 32.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(.6F))
        }

        InteractiveMap(1F, .5F, locationUtility = MainActivity.locationUtility , comp = mainActivity)


        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.info_label), textAlign = TextAlign.Center, fontSize = FONT_SIZE)
        Spacer(modifier = Modifier.height(16.dp))
        Row() {
            var seconds : Int = MainActivity.thisViewModel.thisPath?.endTime?.substring(6, 8)?.toInt()!! -
                    MainActivity.thisViewModel.thisPath?.startTime?.substring(6, 8)?.toInt()!!;
            var minuites = MainActivity.thisViewModel.thisPath?.endTime?.substring(3, 5)?.toInt()!! -
                    MainActivity.thisViewModel.thisPath?.startTime?.substring(3, 5)?.toInt()!!
            var hours = MainActivity.thisViewModel.thisPath?.endTime?.substring(0, 2)?.toInt()!! -
                    MainActivity.thisViewModel.thisPath?.startTime?.substring(0, 2)?.toInt()!!
            if(seconds < 0){
                seconds += 60
                minuites--
            }
            if(minuites < 0){
                minuites += 60
                hours--
            }
            Text(stringResource(R.string.date_label) + " " + (MainActivity.thisViewModel.thisPath?.date ?: " "),
                Modifier.weight(0.5F), textAlign = TextAlign.Start, fontSize = FONT_SIZE)
            Text(stringResource(R.string.time_label) + " " +
                    String.format("%02d", hours) + ":" +
                    String.format("%02d", minuites) + ":" +
                    String.format("%02d",  seconds),
                Modifier.weight(0.5F), textAlign = TextAlign.End, fontSize = FONT_SIZE)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row() {
            Text(stringResource(R.string.length_label)  + " " + (MainActivity.thisViewModel.thisPath?.length + " Feet"?: " "),
                Modifier.weight(0.5F), textAlign = TextAlign.Start, fontSize = FONT_SIZE)
            val df = DecimalFormat("#.###")
            Text(stringResource(R.string.average_speed_label) + "\n" + (df.format(MainActivity.thisViewModel.thisPath?.length?.toDouble()
                ?.div((MainActivity.thisViewModel.thisPath?.endTime?.substring(0, 2)?.toInt()!! -
                        MainActivity.thisViewModel.thisPath?.startTime?.substring(0, 2)?.toInt()!!) * 3600 +
                        (MainActivity.thisViewModel.thisPath?.endTime?.substring(3, 5)?.toInt()!! -
                        MainActivity.thisViewModel.thisPath?.startTime?.substring(3, 5)?.toInt()!!) * 60 +
                        (MainActivity.thisViewModel.thisPath?.endTime?.substring(6, 8)?.toInt()!! -
                                MainActivity.thisViewModel.thisPath?.startTime?.substring(6, 8)?.toInt()!!))) + "ft/s"
                ?: " "),
                Modifier.weight(0.5F), textAlign = TextAlign.End, fontSize = FONT_SIZE)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth() .padding(8.dp),
            enabled = true,
            onClick =
            {
                navController.navigate("home_screen");
            }
        ){
            Text("New Route", textAlign = TextAlign.Center)
            //}
        }
    }
}
