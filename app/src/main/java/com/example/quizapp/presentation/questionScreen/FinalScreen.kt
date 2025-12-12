package com.example.quizapp.presentation.questionScreen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.quizapp.R
import com.example.quizapp.presentation.NavigationItem
import com.example.quizapp.presentation.utils.ColorPalette
import com.example.quizapp.ui.theme.Lexend
import java.util.Locale


@Composable
fun FinalScreen(
    controller: NavHostController,
    quizId: Long? = null,
    totalCorrectAnswers: Int = 0,
    totalQuestions: Int = 0,
    time: Long = 0
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorPalette.bgDark),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(fraction = 0.95f)

        ) {
            TopBarPart(onCloseQuiz = {
                controller.navigate(NavigationItem.HOME.route) {
                    popUpTo(NavigationItem.HOME.route) {
                        saveState = true
                    }
                }
            })

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Well Done!", fontSize = 30.sp, color = Color.White,
                    fontFamily = Lexend, fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    "You have completed the quiz.",
                    fontSize = 16.sp, color = Color.White,
                    fontFamily = Lexend
                )

                Spacer(modifier = Modifier.height(50.dp))

                CircularProgressBorder(
                    progress = (totalCorrectAnswers / totalQuestions.toFloat()),
                    size = 200.dp,
                    strokeWidth = 24.dp,
                    finalScore = "$totalCorrectAnswers/$totalQuestions"
                )

                Spacer(modifier = Modifier.height(50.dp))

                InfoItem(
                    title = "Correct", text = "$totalCorrectAnswers",
                    title2 = "Incorrect", text2 = "${totalQuestions - totalCorrectAnswers}"
                )

                Spacer(modifier = Modifier.height(20.dp))

                val minutes = (time / 1000) / 60
                val seconds = (time / 1000) % 60

                val formatted = String.format(
                    Locale.getDefault(),
                    "%02d:%02d", minutes, seconds
                )

                InfoItem(
                    title = "Completion", text = "100%",
                    title2 = "Total Time", text2 = "$formatted Mins"
                )

                Spacer(modifier = Modifier.weight(1f))

                LastButtons(
                    navigateToHome = {
                        controller.navigate(
                            NavigationItem.HOME.route
                        ) {
                            popUpTo(NavigationItem.HOME.route) {
                                saveState = true
                            }
                        }
                    },
                    navigateToRetry = {
                        controller.navigate("questionScreen/$quizId") {
                            popUpTo(NavigationItem.HOME.route) {
                                saveState = true
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

            }
        }
    }
}

@Composable
fun TopBarPart(
    onCloseQuiz: () -> Unit = {}
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
            text = "Final Score", color = Color.White, fontFamily = Lexend,
            fontWeight = FontWeight.Bold, fontSize = 24.sp
        )

        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = {

            }
        ) {
            Icon(
                painter = painterResource(R.drawable.share), contentDescription = "Share Icon",
                tint = Color.White
            )
        }
    }
}

@Composable
fun CircularProgressBorder(
    progress: Float, // 0f a 1f
    size: Dp = 180.dp,
    strokeWidth: Dp = 8.dp,
    finalScore: String = "",
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(size)) {
        Canvas(modifier = Modifier.size(size)) {
            val diameter = size.toPx()

            // fondo del círculo
            drawArc(
                color = ColorPalette.bgInitialCards,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx())
            )

            // progreso
            drawArc(
                color = ColorPalette.primaryGreen,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = Stroke(
                    width = strokeWidth.toPx(),
                    cap = StrokeCap.Round // bordes redondeados
                )
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = finalScore,
                fontSize = 48.sp,
                color = Color.White
            )

            Text(
                text = "Correct Answers",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun LastButtons(
    navigateToHome: () -> Unit = {},
    navigateToRetry: () -> Unit = {}
) {

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ColorPalette.primaryGreen
        ),
        onClick = {
            navigateToRetry.invoke()
        },
        shape = RoundedCornerShape(8.dp)
    ) {

        Text(
            "Retry Quiz",
            style = MaterialTheme.typography.labelLarge
                .copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Lexend,
                    fontSize = 18.sp
                )
        )
    }

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ColorPalette.bgInitialCards
        ),
        onClick = {
            navigateToHome.invoke()
        },
        shape = RoundedCornerShape(8.dp)
    ) {


        Text(
            "Return Home",
            style = MaterialTheme.typography.labelLarge
                .copy(
                    color = ColorPalette.primaryGreen,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Lexend,
                    fontSize = 18.sp
                )
        )
    }
}

@Composable
fun InfoItem(
    title: String,
    text: String,
    title2: String,
    text2: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            HorizontalDivider(
                color = ColorPalette.greenButton,
                thickness = 2.dp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                title, fontWeight = FontWeight.Normal, color = Color.Gray,
                fontFamily = Lexend, fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text, fontWeight = FontWeight.Bold, color = Color.White,
                fontFamily = Lexend, fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            HorizontalDivider(
                color = ColorPalette.greenButton,
                thickness = 2.dp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                title2, fontWeight = FontWeight.Normal, color = Color.Gray,
                fontFamily = Lexend, fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(10.dp))


            Text(
                text2, fontWeight = FontWeight.Bold, color = Color.White,
                fontFamily = Lexend, fontSize = 20.sp
            )
        }
    }

}