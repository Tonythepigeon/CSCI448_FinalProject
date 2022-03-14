package com.csci448.pathmapper.ui.screens

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

@Composable
fun PastRoutesList(routeList: List<String>?, onSelectRoute: (route: String) -> Unit){
    if(routeList != null){
        LazyColumn{
            items(routeList){ route -> RouteRow(route, onSelectRoute)}
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PastRoutesScreen(){
    Column(modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 20.dp)){
        Title(stringResource(R.string.past_routes_page_label))
        Spacer(modifier = Modifier.height(16.dp))
        val nameIn = stringResource(R.string.search_box_label)
        var text by rememberSaveable { mutableStateOf(nameIn) }
        TextField(
            value = "",
            onValueChange = { text = it },
            label = { Text(stringResource(R.string.search_box_label), fontSize = FONT_SIZE) }
        )
        NewButton(stringResource(R.string.search_box_label, true)) {}
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.battery_label), fontSize = FONT_SIZE)
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.color_label), fontSize = FONT_SIZE)
        Spacer(modifier = Modifier.height(16.dp))
    }
}