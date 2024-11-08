package com.dag.nexq_app.presentation.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dag.nexq_app.R
import com.dag.nexq_app.presentation.home.components.HomeCard
import com.dag.nexq_app.presentation.home.components.HomeHeader

@Composable
fun Home(
    homeVM: HomeVM = hiltViewModel()
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        HomeHeader()
        Spacer(modifier = Modifier.size(32.dp))
        HomeCard(
            text = stringResource(id = R.string.home_screen_create_quiz)
        ){
            homeVM.joinQuiz()
        }
    }
}


@Composable
@Preview
fun HomePreview(){
    Home()
}