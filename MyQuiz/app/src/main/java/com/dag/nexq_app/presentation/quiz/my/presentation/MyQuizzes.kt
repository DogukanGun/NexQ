package com.dag.nexq_app.presentation.quiz.my.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dag.nexq_app.R

@Composable
fun MyQuizzes(
    myQuizzesVM: MyQuizzesVM = hiltViewModel()
){
    val quizzes = myQuizzesVM.getMyQuizzes().collectAsState(initial = emptyList())
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        item {
            Text(
                text = stringResource(id = R.string.my_quizzes_title),
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.size(4.dp))
            Divider()
            Spacer(modifier = Modifier.size(8.dp))
        }
        items(quizzes.value){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Text(
                    text = it.name ?:
                        stringResource(id = R.string.my_quizzes_unknown_quiz_cell),
                    modifier = Modifier.padding(4.dp)
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}


@Composable
@Preview
fun MyQuizzesPreview(){
    MyQuizzes()
}