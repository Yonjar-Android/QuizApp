package com.example.quizapp.presentation.createQuiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.quizapp.R
import com.example.quizapp.presentation.utils.ColorPalette
import com.example.quizapp.ui.theme.Lexend

@Composable
fun CreateQuizScreen(controller: NavHostController) {

    var title by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorPalette.bgDark),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(fraction = 0.94f),
        ) {
            TopBarIcons(controller)

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                "Quiz Title", color = Color.White,
                fontFamily = Lexend,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(5.dp))

            TextField(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        color = ColorPalette.borderTextField,
                        RoundedCornerShape(8.dp)
                    ),

                value = title, onValueChange = { title = it },
                placeholder = {
                    Text(
                        "Enter a title for your quiz",
                        fontFamily = Lexend,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = ColorPalette.bgTextField2,
                    unfocusedContainerColor = ColorPalette.bgTextField2,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = ColorPalette.primaryGreen
                )
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                "Questions", color = Color.White,
                fontFamily = Lexend,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            QuestionItem()

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .drawBehind {
                        val stroke = Stroke(
                            width = 3f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f), 0f)
                        )
                        // Draw rounded rectangle with dashed border
                        drawRoundRect(
                            color = ColorPalette.borderTextField,
                            style = stroke,
                            cornerRadius = CornerRadius(20f, 20f)
                        )
                    }
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ColorPalette.bgDark
                    ),
                    border = BorderStroke(0.dp, ColorPalette.borderTextField),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.plus),
                            contentDescription = "Add Question",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            "Add Question", fontSize = 16.sp, color = Color.White,
                            fontFamily = Lexend
                        )
                    }

                }
            }

        }
    }

}

@Composable
fun TopBarIcons(controller: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = {
                controller.navigateUp()
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.close), contentDescription = "Close Icon",
                tint = Color.White
            )
        }

        Text(
            "Quizzes", color = Color.White, fontFamily = Lexend,
            fontWeight = FontWeight.Bold, fontSize = 24.sp
        )

        TextButton(
            onClick = {}
        ) {
            Text(
                "Save", color = ColorPalette.primaryGreen, fontWeight = FontWeight.Bold,
                fontFamily = Lexend, fontSize = 18.sp
            )
        }
    }
}

@Composable
fun QuestionItem() {

    var question by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                ColorPalette.bgTextField,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Question 1", color = Color.White,
                fontFamily = Lexend,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )

            IconButton(
                modifier = Modifier.size(22.dp),
                onClick = {

                }) {
                Icon(
                    modifier = Modifier,
                    painter = painterResource(R.drawable.delete),
                    contentDescription = "Delete Icon",
                    tint = Color.White,
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    color = ColorPalette.borderTextField,
                    RoundedCornerShape(8.dp)
                ),

            value = question, onValueChange = { question = it },
            placeholder = {
                Text(
                    "Enter a title for your quiz",
                    fontFamily = Lexend,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = ColorPalette.bgTextField2,
                unfocusedContainerColor = ColorPalette.bgTextField2,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = ColorPalette.primaryGreen
            )
        )

        Spacer(Modifier.height(10.dp))

        var selectedRadioButton by remember { mutableStateOf(0) }

        AnswerItem(
            selected = selectedRadioButton == 0,
            onSelected = {
                selectedRadioButton = 0
            }
        )
        AnswerItem(
            selected = selectedRadioButton == 1,
            onSelected = {
                selectedRadioButton = 1
            }
        )
        AnswerItem(
            selected = selectedRadioButton == 2,
            onSelected = {
                selectedRadioButton = 2
            }

        )
        AnswerItem(
            selected = selectedRadioButton == 3,
            onSelected = {
                selectedRadioButton = 3
            }
        )

    }
}


@Composable
fun AnswerItem(
    selected: Boolean,
    onSelected: () -> Unit
) {

    var answer by remember { mutableStateOf("") }

    val colorSelected = if (selected) ColorPalette.primaryGreen else ColorPalette.borderTextField

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            modifier = Modifier.weight(1f),
            selected = selected,
            onClick = {
                onSelected()
            },
            colors = RadioButtonDefaults.colors(
                selectedColor = ColorPalette.primaryGreen,
                unselectedColor = ColorPalette.borderTextField
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        TextField(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(9f)
                .border(
                    1.dp,
                    color = colorSelected,
                    RoundedCornerShape(8.dp)
                ),

            value = answer, onValueChange = { answer = it },
            placeholder = {
                Text(
                    "Answer Option A",
                    fontFamily = Lexend,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = ColorPalette.bgTextField2,
                unfocusedContainerColor = ColorPalette.bgTextField2,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = ColorPalette.primaryGreen
            )
        )
    }

    Spacer(Modifier.height(10.dp))
}