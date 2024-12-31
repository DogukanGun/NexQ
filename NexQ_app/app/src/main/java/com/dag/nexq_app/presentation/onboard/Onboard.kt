package com.dag.nexq_app.presentation.onboard

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dag.nexq_app.base.components.CustomButton
import com.dag.nexq_app.base.components.standardAnimation
import kotlinx.coroutines.launch


@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun Onboard(
    onboardVM: OnboardVM = viewModel(),
) {
    val scope = rememberCoroutineScope()
    val pagerState = produceState(
        initialValue = rememberPagerState(initialPage = 0, pageCount = {onboardVM.contents.size}),
        key1 = onboardVM.contents.size
    ) {}.value

    LaunchedEffect(key1 = pagerState.currentPage){
        onboardVM.changeNavbarColor(pagerState.currentPage)
    }
    HorizontalPager(state = pagerState) {
        AnimatedContent(
            targetState = onboardVM.contents[it],
            transitionSpec = { standardAnimation },
            label = ""
        ) { internalState ->
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
                        text = stringResource(id = internalState.buttonText)
                    ) {
                        if (it < pagerState.pageCount - 1){
                            scope.launch {
                                pagerState.animateScrollToPage(
                                    pagerState.currentPage + 1
                                )
                            }
                        }else{
                            onboardVM.goLogin()
                        }
                    }
                }
            }
        }
    }


}

@Preview
@Composable
fun OnboardPreview() {
    Onboard()
}