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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.csci448.pathmapper.R



@Preview(showBackground = true)
@Composable
private fun PreviewRouteDetailsScreen(){
    RouteDetailsScreen(navController = rememberNavController())
}

@Composable
fun RouteDetailsScreen(navController: NavController){
    Column(modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 20.dp)){
        Title("*Route name*")
        Spacer(modifier = Modifier.height(16.dp))
        Text("*Interactive map", fontSize = 48.sp)
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