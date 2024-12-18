package com.dag.nexq_app.presentation.quiz.add.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dag.nexq_app.R
import com.dag.nexq_app.base.components.BackNavigationBar
import com.dag.nexq_app.base.navigation.Destination
import com.dag.nexq_app.presentation.quiz.components.QuizBody
import com.dag.nexq_app.presentation.quiz.components.QuizOption
import com.dag.nexq_app.presentation.quiz.extension.selectOption


@Composable
fun QuizAdd(
    addVM: QuizAddVM = hiltViewModel(),
    navController: NavController
){
    var selectedOption by remember{ mutableStateOf(-1) }
    BackNavigationBar(backButtonClick = { addVM.goBack() }) {
        QuizBody(
            modifier = Modifier.padding(16.dp),
            title = stringResource(id = R.string.quiz_add_title),
            enabled = selectedOption != -1,
            onClick = {
                if (selectedOption == 1){
                    navController.navigate(Destination.AddWithPdf)
                }else if(selectedOption == 2){
                    navController.navigate(Destination.Edit)
                }
            }
        ) {
            QuizOption(
                index = 1,
                option = stringResource(id = R.string.quiz_add_add_pdf),
                clickable = selectedOption != 2
            ) {
                selectedOption = selectedOption.selectOption(1)
            }
            Spacer(modifier = Modifier.size(4.dp))
            QuizOption(
                index = 2,
                option = stringResource(id = R.string.quiz_add_manual),
                clickable = selectedOption != 1
            ){
                selectedOption = selectedOption.selectOption(2)
            }
        }
    }

}

@Composable
@Preview
fun AddQuizPreview(){
    QuizAdd(
        navController = rememberNavController()
    )
}