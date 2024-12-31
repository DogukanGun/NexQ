package com.dag.nexq_app.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dag.nexq_app.base.components.CustomButton

@Composable
fun HomeCard(
    text:String,
    customButtonText:String,
    onClick:()->Unit
){
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge
                .copy(color = Color.White)
        )
        Spacer(modifier = Modifier.size(32.dp))
        CustomButton(
            modifier = Modifier,
            backgroundColor = Color.White,
            innerPadding = 8.dp,
            text = customButtonText,
            textColor = Color.Black
        ) {
           onClick()
        }
    }
}

@Composable
@Preview
fun HomeCardPreview(){
    HomeCard(text = "Hi", customButtonText = "Join") {}
}