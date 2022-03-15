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
private fun PreviewOptionsScreen(){
    OptionsScreen(navController = rememberNavController())
}

@Composable
fun OptionsScreen(navController: NavController){
    Column(modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 20.dp)){
        Title(stringResource(R.string.options_page_label))
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.advanced_color_label), fontSize = FONT_SIZE)
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.advanced_battery_options), fontSize = FONT_SIZE)
    }
}