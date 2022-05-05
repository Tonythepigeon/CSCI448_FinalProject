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
import java.text.DecimalFormat


@Composable
private fun RouteRow(path: Path, onSelectRoute: (route: String) -> Unit){
    Card(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
        ) {
            Text(path.date)
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Start time: " + path.startTime,
                    Modifier.weight(0.45F),
                    textAlign = TextAlign.Start
                )
                Text("-", Modifier.weight(0.1F), textAlign = TextAlign.Center)
                Text(
                    "End time: " + path.endTime,
                    Modifier.weight(0.45F),
                    textAlign = TextAlign.End
                )

            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Length of Path: " + path.length + "ft",
                    Modifier.weight(0.5F),
                    textAlign = TextAlign.Start
                )
                val df = DecimalFormat("#.###")
                Text(
                    "Speed: " + (df.format(
                        path.length.toDouble()
                            .div(
                                (path.endTime.substring(0, 2).toInt() -
                                        path.startTime.substring(0, 2).toInt()) * 3600 +
                                        (path.endTime.substring(3, 5).toInt() -
                                                path.startTime.substring(3, 5).toInt()) * 60 +
                                        (path.endTime.substring(6, 8).toInt() -
                                                path.startTime.substring(6, 8).toInt())
                            )
                    ) + "ft/s"
                        ?: " "), Modifier.weight(0.5F), textAlign = TextAlign.End
                )
            }
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

    Column(modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 20.dp)) {
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
        Column(modifier = Modifier.fillMaxSize() ) {
            Column(modifier = Modifier.weight(0.93F)) {
                PastRoutesList(routeList = pathListState.value, onSelectRoute = {})
            }
            Button(
                modifier = Modifier.fillMaxWidth().padding(8.dp).weight(0.07F),
                enabled = true,
                onClick =
                {
                    MainActivity.thisViewModel.deleteAllData()
                }
            ) {
                Text("Delete Past Routes", textAlign = TextAlign.Center)
                //}
            }
        }
    }
}