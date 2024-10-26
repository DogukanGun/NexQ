package com.dag.myquiz.presentation.onboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dag.myquiz.base.components.CustomButton


@Composable
fun Onboard(
    onboardVM: OnboardVM = viewModel()
){
    val state = onboardVM.viewState.collectAsState()

    when(state.value) {
        is OnboardVS.OnboardContent -> {
            val internalState = (state.value as OnboardVS.OnboardContent)
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(internalState.color)
            ) {
                Image(
                    painter = painterResource(id = internalState.image),
                    modifier = Modifier.fillMaxSize(fraction = 0.5f),
                    contentDescription = "Image"
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Text(
                            text = stringResource(id = internalState.title),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = stringResource(id = internalState.subtitle),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    CustomButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        backgroundColor = internalState.color,
                        text = stringResource(id = internalState.buttonText)) {
                    }

                }
            }
        }
        OnboardVS.StartUserOps -> {

        }
    }
}

@Preview
@Composable
fun OnboardPreview(){
    Onboard()
}