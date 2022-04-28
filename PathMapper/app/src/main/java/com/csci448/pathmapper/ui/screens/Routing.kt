package com.csci448.pathmapper.ui.screens

import android.app.Activity
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.csci448.pathmapper.InteractiveMap
import com.csci448.pathmapper.MainActivity
import com.csci448.pathmapper.R


@Composable
private fun RouteRow(routeID: String, onSelectRoute: (route: String) -> Unit){
    Card(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .clickable { onSelectRoute(routeID) }

    ){
        Row{
            Text(routeID)
            //...More stuff to include
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun PreviewRoutingScreen(){
//    RoutingScreen(navController = rememberNavController())
//}

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun RoutingScreen(navController: NavController, mainActivity : ComponentActivity){
    Column(modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 20.dp)){
        Title(stringResource(R.string.route_page_label))
        Spacer(modifier = Modifier.height(16.dp))
//        NewButton(stringResource(R.string.route_options_button_label, true)) {navController.navigate("options_screen")}
//        Spacer(modifier = Modifier.height(16.dp))
//        NewButton(stringResource(R.string.map_label, true)) {navController.navigate("route_details_screen")}
//        Spacer(modifier = Modifier.height(16.dp))

        InteractiveMap(1F, .8F, locationUtility = MainActivity.locationUtility , comp = mainActivity)

//        Row {
//            Button(
//                modifier = Modifier.weight(.5F),
//                enabled = true,
//                onClick = { }
//            ){
//                Text(stringResource(R.string.lap_button_label), textAlign = TextAlign.Center)
//            }
//            Spacer(modifier = Modifier.width(24 .dp))
            Button(
                modifier = Modifier.fillMaxWidth() .padding(8.dp),
                enabled = true,
                onClick = {navController.navigate("route_details_screen")}
            ){
                Text(stringResource(R.string.end_button_label), textAlign = TextAlign.Center)
            //}
        }
    }
}