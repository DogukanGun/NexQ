package com.dag.nexq_app.presentation.quiz.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dag.nexq_app.R
import com.dag.nexq_app.base.components.CustomButton
import com.dag.nexq_app.presentation.quiz.extension.getOption

@Composable
fun QuizBody(
    title: String,
    enabled: Boolean = true,
    editMode: Boolean = false,
    buttonText: String = "Start",
    onClick: () -> Unit,
    onTextChange: ((text: String) -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    var questionText by remember {
        mutableStateOf("")
    }
    Surface(
        color = Color.White,
        border = BorderStroke(1.dp, Color.Gray),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            if(editMode)
                QuizEditTextField(
                    text = questionText,
                    placeholder = title,
                    onTextChange = {fieldText->
                        questionText = fieldText
                        onTextChange?.apply {
                            this(fieldText)
                        }
                    }
                )
            else
                Text(
                    title,
                    style = MaterialTheme.typography.headlineLarge
                )
            AnimatedVisibility(visible = editMode.not()) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.4f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                        .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    painter = painterResource(id = R.drawable.quiz_hero),
                    contentDescription = "Quiz Hero"
                )
            }
            Column {
                content()
            }
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = enabled,
                backgroundColor = MaterialTheme.colorScheme.primary,
                text = buttonText
            ) {
                onClick()
            }
        }
    }
}

@Composable
fun QuizOption(
    index: Int,
    option: String,
    clickable: Boolean = true,
    editMode: Boolean = false,
    onTextChange: ((text:String)->Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    var clicked by remember {
        mutableStateOf(false)
    }
    var optionTextFieldValue by remember {
        mutableStateOf("")
    }
    val buttonShape = RoundedCornerShape(8.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(buttonShape)
            .border(2.dp, Color.Gray, buttonShape)
            .background(
                if (clicked.not()) Color.Transparent else Color.Gray
            )
            //if edit mode is on then cell is always not clickable
            .clickable(enabled = clickable && editMode.not()) {
                clicked = clicked.not()
                onClick?.apply { this() }
            }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = index.getOption().plus("."))
        Spacer(modifier = Modifier.size(8.dp))
        if (editMode)
            QuizEditTextField(
                text = optionTextFieldValue,
                placeholder = option,
                onTextChange = {fieldText->
                    optionTextFieldValue = fieldText
                    onTextChange?.apply {
                        this(fieldText)
                    }
                }
            )
        else
            Text(text = option)


    }
}

@Composable
fun QuizEditTextField(
    text: String,
    placeholder: String,
    onTextChange: (String) -> Unit
){
    val textFieldShape = RoundedCornerShape(4.dp)
    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        modifier = Modifier
            .height(30.dp)
            .background(Color.Transparent, shape = textFieldShape)
            .border(1.dp, Color.Gray, textFieldShape),
        textStyle = MaterialTheme.typography.labelSmall,
        singleLine = true,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp) // Horizontal padding
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterStart // Align text to start
            ) {
                // Show placeholder when text is empty
                if (text.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                // The actual text field content
                innerTextField()
            }
        }
    )
}

@Preview
@Composable
fun QuizOptionWithEditModePreview(){
    Surface {
        QuizOption(
            index = 1,
            option = "test",
            editMode = true
        ) {}
    }
}

@Preview
@Composable
fun AddQuizEditPreview(){
    Surface {
        QuizBody(
            title = "Test",
            onClick = {},
            editMode = true
        ) {
            QuizOption(index = 1, editMode = true, option = "test") {}
            QuizOption(index = 1, editMode = true, option = "test") {}
            QuizOption(index = 1, editMode = true, option = "test") {}
            QuizOption(index = 1, editMode = true, option = "test") {}
        }
    }
}

@Preview
@Composable
fun AddQuizPreview() {
    QuizBody(title = "Test", onClick = {}) {
        QuizOption(index = 1, option = "test") {}
        QuizOption(index = 1, option = "test") {}
    }
}