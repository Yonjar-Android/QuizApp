package com.example.quizapp.presentation.questionScreen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.quizapp.R
import com.example.quizapp.presentation.NavigationItem
import com.example.quizapp.presentation.classes.OptionModel
import com.example.quizapp.presentation.utils.ColorPalette
import com.example.quizapp.ui.theme.Lexend
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuestionScreen(
    quizId: Long?,
    controller: NavHostController,
    viewModel: QuestionViewModel = koinViewModel()
) {

    LaunchedEffect(Unit) {
        if (quizId == null) {
            controller.navigateUp()
        } else {
            viewModel.loadQuestions(quizId)
            viewModel.startTimer()
        }
    }

    val context = LocalContext.current

    val quiz by viewModel.quiz.collectAsStateWithLifecycle()

    val currentIndex by viewModel.currentIndex.collectAsStateWithLifecycle()

    val selectedOptions by viewModel.selectedOptions.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(ColorPalette.bgDark),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(fraction = 0.95f)

        ) {
            TopBarPart(
                questionNumber = currentIndex + 1,
                totalQuestions = quiz?.questions?.size ?: 0,
                quizTitle = quiz?.title ?: "", onCloseQuiz = {
                    controller.navigateUp()
                })

            Spacer(modifier = Modifier.height(20.dp))

            // Bar of progress in the quiz

            var progress by remember { mutableStateOf(0f) }
            progress = (currentIndex + 1) / (quiz?.questions?.size ?: 1).toFloat()

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(ColorPalette.bgInitialCards)
            ) {
                Box(
                    Modifier
                        .fillMaxWidth(progress)
                        .fillMaxHeight()
                        .background(ColorPalette.primaryGreen)
                )
            }



            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = quiz?.questions[currentIndex]?.question ?: "",
                color = Color.White,
                fontFamily = Lexend,
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp,
                lineHeight = 35.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (quiz != null) {

                val question = quiz?.questions?.getOrNull(currentIndex)

                question?.options?.forEach {
                    AnswerItem(
                        option = it,
                        selectedOptionId = selectedOptions[question.id],
                        onSelect = { selectedId ->
                            viewModel.selectOption(question.id, selectedId)
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }


            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ColorPalette.primaryGreen
                ),
                onClick = {
                    val question = quiz?.questions?.getOrNull(currentIndex) ?: return@Button

                    val selectedOptionForCurrent =
                        selectedOptions[question.id]

                    if (selectedOptionForCurrent == null) {
                        Toast
                            .makeText(context, "Please select an answer", Toast.LENGTH_SHORT)
                            .show()
                        return@Button
                    }

                    if (currentIndex == quiz?.questions?.size?.minus(1)) {
                        val finalTime = viewModel.stopTimer()

                        controller.navigate(
                            "finalScreen/${viewModel.sumOfCorrectAnswers()}/${quiz?.questions?.size}/${quiz?.id}/$finalTime"
                        ) {
                            popUpTo(NavigationItem.QUESTION.route) {
                                inclusive = true
                            }
                        }
                    } else {

                        viewModel.nextQuestion()
                    }

                },
                shape = RoundedCornerShape(8.dp)
            ) {

                val textButton = if (currentIndex == quiz?.questions?.size?.minus(1)) {
                    "Finish"
                } else {
                    "Next"
                }

                Text(
                    textButton,
                    style = MaterialTheme.typography.labelLarge
                        .copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Lexend,
                            fontSize = 18.sp
                        )
                )
            }

        }
    }

    BackHandler {
        viewModel.previousQuestion()
    }
}

@Composable
fun TopBarPart(
    questionNumber: Int,
    totalQuestions: Int,
    quizTitle: String,
    onCloseQuiz: () -> Unit
) {
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
                onCloseQuiz.invoke()
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.close), contentDescription = "Close Icon",
                tint = Color.White
            )
        }

        Text(
            quizTitle, color = Color.White, fontFamily = Lexend,
            fontWeight = FontWeight.Bold, fontSize = 24.sp
        )
        Text(
            "$questionNumber/$totalQuestions",
            color = ColorPalette.primaryGreen,
            fontWeight = FontWeight.Bold,
            fontFamily = Lexend,
            fontSize = 18.sp
        )
    }
}

@Composable
fun AnswerItem(
    option: OptionModel,
    selectedOptionId: Long?,
    onSelect: (Long) -> Unit
) {
    val isSelected = selectedOptionId == option.id

    val backgroundColor = if (isSelected) ColorPalette.bgInitialCards else ColorPalette.bgTextField

    Row(
        modifier = Modifier
            .fillMaxWidth()

            .clip(RoundedCornerShape(8.dp))
            .border(
                1.dp, ColorPalette.borderTextField,
                RoundedCornerShape(8.dp)
            )
            .background(backgroundColor)
            .padding(8.dp)
            .clickable {
                onSelect.invoke(option.id)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            option.answer,
            color = Color.White,
            fontFamily = Lexend,
            fontSize = 16.sp
        )

        RadioButton(
            selected = isSelected,
            onClick = {
                onSelect.invoke(option.id)
            },
            colors = RadioButtonDefaults.colors(
                selectedColor = ColorPalette.primaryGreen,
                unselectedColor = ColorPalette.borderTextField
            )
        )
    }
}