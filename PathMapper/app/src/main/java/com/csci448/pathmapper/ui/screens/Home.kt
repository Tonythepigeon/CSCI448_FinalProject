package com.csci448.pathmapper.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
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
import androidx.compose.runtime.setValue
import com.csci448.pathmapper.R

val FONT_SIZE = 18.sp


@Composable
fun Title(stringIn: String){
    Text(stringIn, color= Color.Blue, fontSize = 32.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
}
@Composable
fun NewButton(text: String, enabled: Boolean = true, onClick: () -> Unit){
    Button(
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        onClick = onClick
    ){
        Text(text, textAlign = TextAlign.Center)
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCharacterDetailScreen(){
    Column(modifier = Modifier.padding(start = 12.dp, end = 12.dp)){
        Title(stringResource(R.string.begin_route_page_label))
        Spacer(modifier = Modifier.height(16.dp))
        val nameIn = stringResource(R.string.name_label)
        var text by rememberSaveable { mutableStateOf(nameIn) }
        TextField(
            value = "",
            onValueChange = { text = it },
            label = { Text(stringResource(R.string.name_label), fontSize = FONT_SIZE) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.battery_label), fontSize = FONT_SIZE)
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.color_label), fontSize = FONT_SIZE)
        Spacer(modifier = Modifier.height(16.dp))
        NewButton(stringResource(R.string.start_button_label, true)) {}
    }
}