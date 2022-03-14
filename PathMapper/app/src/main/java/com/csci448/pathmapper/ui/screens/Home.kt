package com.csci448.pathmapper.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
@Composable
fun RadioGroup(
    options: List<String>?,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit) =
    options?.forEach { item ->
        Row(
            Modifier.padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = (item == selectedOption),
                onClick = {onOptionSelected(item) },
            )
            ClickableText(
                text = AnnotatedString(item),
                modifier = Modifier.padding(start = 4.dp),
                style = TextStyle(fontSize = FONT_SIZE),
                onClick = {
                    onOptionSelected(item)
                }
            )
        }
    }
@Composable
fun ColorRadioGroup(
    options: List<Color>?,
    selectedOption: Color?,
    onOptionSelected: (Color) -> Unit) =
    Row (){
        options?.forEach { item ->
            RadioButton(
                selected = (item == selectedOption),
                onClick = { onOptionSelected(item) },
                colors = RadioButtonDefaults.colors(selectedColor = item, unselectedColor = item)
            )
        }
    }

@Preview(showBackground = true)
@Composable
fun HomeScreen(){
    Column(modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 20.dp)){
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
        RadioGroup(listOf(stringResource(R.string.battery_efficient_label),
            stringResource(R.string.battery_moderate_label),
            stringResource(R.string.battery_most_accurate_label)),
            stringResource(R.string.battery_efficient_label)) { option -> print(option)}
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.color_label), fontSize = FONT_SIZE)
        Spacer(modifier = Modifier.height(16.dp))
        ColorRadioGroup(listOf(Color.Blue, Color.Cyan, Color.Yellow, Color.Green, Color.Magenta, Color.Red),
            selectedOption = Color.Blue) {option -> print(option)}
        Spacer(modifier = Modifier.height(16.dp))
        NewButton(stringResource(R.string.start_button_label, true)) {}
    }
}