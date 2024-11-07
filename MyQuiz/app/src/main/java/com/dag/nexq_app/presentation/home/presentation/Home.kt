package com.dag.nexq_app.presentation.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Home(){
    Column {
        Text("HiText", color = Color.Red)
    }
}


@Composable
@Preview
fun HomePreview(){
    Home()
}