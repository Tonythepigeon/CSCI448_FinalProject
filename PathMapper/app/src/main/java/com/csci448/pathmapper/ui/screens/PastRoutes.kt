package com.csci448.pathmapper.ui.screens

import android.widget.Toast
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.csci448.pathmapper.MainActivity
import com.csci448.pathmapper.R
import com.csci448.pathmapper.data.database.Path


@Composable
private fun RouteRow(path: Path, onSelectRoute: (route: String) -> Unit){
    Card(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .fillMaxWidth()
    ){
        Column() {
            Text(path.date)
            Text("Start time: " + path.startTime + " - End time: " + path.endTime)
            Text("Length of Path: " + path.length)
        }
    }
}

@Composable
fun PastRoutesList(routeList: List<Path>?, onSelectRoute: (route: String) -> Unit){
    if(routeList != null){
        LazyColumn{
            items(routeList){ route -> RouteRow(route, onSelectRoute)}
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun PreviewPastRoutesScreen(){
//    PastRoutesScreen(navController = rememberNavController())
//}

@Composable
fun PastRoutesScreen(navController: NavController){
    val pathListState = MainActivity.thisViewModel.pathListLiveData.observeAsState()

    Column(modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 20.dp)){
        Title(stringResource(R.string.past_routes_page_label))
//        Spacer(modifier = Modifier.height(16.dp))
//        val nameIn = stringResource(R.string.search_box_label)
//        var text by rememberSaveable { mutableStateOf(nameIn) }
//        TextField(
//            value = "",
//            onValueChange = { text = it },
//            label = { Text(stringResource(R.string.search_box_label), fontSize = FONT_SIZE) }
//        )
//        NewButton(stringResource(R.string.search_box_label, true)) { }
        Spacer(modifier = Modifier.height(16.dp))
        PastRoutesList(routeList = pathListState.value, onSelectRoute = {})
    }
}