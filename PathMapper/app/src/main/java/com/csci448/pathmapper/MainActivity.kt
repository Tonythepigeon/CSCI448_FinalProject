package com.csci448.pathmapper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.csci448.pathmapper.ui.screens.HomeScreen
import com.csci448.pathmapper.ui.theme.PathMapperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityContent()
        }
    }
    @Preview(showSystemUi = true, showBackground = true)
    @Composable
    private fun PreviewMainActivity(){
        MainActivityContent()
    }
}


@Composable
fun MainActivityContent(){
    PathMapperTheme() {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            HomeScreen()
        }
    }
}