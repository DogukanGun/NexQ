package com.dag.nexq_app.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dag.nexq_app.R


@Composable
fun HomeHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = stringResource(id = R.string.home_screen_header_title),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(id = R.string.home_screen_header_subtitle),
                style = MaterialTheme.typography.titleSmall
            )
        }
        Icon(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(32.dp)
                .clip(CircleShape)
                .background(Color.Cyan),
            painter = painterResource(id = R.drawable.baseline_bolt),
            contentDescription = ""
        )
    }
}

@Composable
@Preview
fun HomeHeaderPreview() {
    HomeHeader()
}