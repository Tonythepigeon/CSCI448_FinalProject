package com.csci448.pathmapper.ui.screens

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.csci448.pathmapper.MainActivity
import com.csci448.pathmapper.R

val FONT_SIZE = 18.sp


@Composable
fun Title(stringIn: String){
    Text(stringIn, color= Color.Blue, fontSize = 32.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
}
@Composable
fun NewButton(text: String, enabled: Boolean = true, onClick: () -> Unit){
    Button(
        modifier = Modifier.fillMaxWidth() .padding(8.dp),
        enabled = enabled,
        onClick = onClick
    ){
        Text(text, textAlign = TextAlign.Center)
    }
}
@Composable
fun radioGroup(
    radioOptions: List<String> = listOf(),
    title: String = "",
    cardBackgroundColor: Color = Color(0xFFFEFEFA)
):String{
    if (radioOptions.isNotEmpty()){
        val (selectedOption, onOptionSelected) = remember {
            mutableStateOf(radioOptions[0])
        }

        Card(
            backgroundColor = cardBackgroundColor,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            elevation = 8.dp,
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                Modifier.padding(10.dp)
            ) {
                Text(
                    text = title,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(5.dp),
                )

                radioOptions.forEach { item ->
                    Row(
                        Modifier.padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (item == selectedOption),
                            onClick = { onOptionSelected(item) }
                        )

                        val annotatedString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(fontWeight = FontWeight.Bold)
                            ){ append("  $item  ") }
                        }

                        ClickableText(
                            text = annotatedString,
                            onClick = {
                                onOptionSelected(item)
                            }
                        )
                    }
                }
            }
        }
        return selectedOption
    }else{
        return ""
    }
}

@Composable
fun colorRadioGroup(
    radioOptions: List<Color> = listOf(),
    title: String = "",
    cardBackgroundColor: Color = Color(0xFFFEFEFA)
):String{
    if (radioOptions.isNotEmpty()){
        val (selectedOption, onOptionSelected) = remember {
            mutableStateOf(radioOptions[0])
        }

        Card(
            backgroundColor = cardBackgroundColor,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            elevation = 8.dp,
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                Modifier.padding(10.dp)
            ) {
                Text(
                    text = title,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(5.dp),
                )
                Row(){
                radioOptions.forEach { item ->
//                    Column(
//                        //Modifier.padding(5.dp),
//                        //verticalAlignment = Alignment.CenterVertically
//                    ) {
                        RadioButton(
                            selected = (item == selectedOption),
                            onClick = { onOptionSelected(item) },
                            colors = RadioButtonDefaults.colors(selectedColor = item, unselectedColor = item)
                        )
//
                    }

                }
            }
        }
        return selectedOption.toString()
    }else{
        return ""
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

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun HomeScreen(navController: NavController, mainActivity: ComponentActivity){
    Column(modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 20.dp)){
        Title(stringResource(R.string.begin_route_page_label))
        Spacer(modifier = Modifier.height(16.dp))
//        val nameIn = stringResource(R.string.name_label)
//        var text by rememberSaveable { mutableStateOf(nameIn) }
//        TextField(
//            value = "",
//            onValueChange = { text = it },
//            label = { Text(stringResource(R.string.name_label), fontSize = FONT_SIZE) }
//        )
//        Spacer(modifier = Modifier.height(16.dp))
        radioGroup(listOf(
            stringResource(R.string.battery_efficient_label),
            stringResource(R.string.battery_moderate_label),
            stringResource(R.string.battery_most_accurate_label)),
            stringResource(R.string.battery_label))
        Spacer(modifier = Modifier.height(16.dp))
        colorRadioGroup(listOf(Color.Blue, Color.Cyan, Color.Yellow, Color.Green, Color.Magenta, Color.Red), stringResource(R.string.color_label))
        Spacer(modifier = Modifier.height(16.dp))
        NewButton(stringResource(R.string.start_button_label, true)) {navController.navigate("routing_screen");
            MainActivity.locationLogger(true, 1000, mainActivity)}
    }
}