package com.example.quizapp.presentation.firstScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.R
import com.example.quizapp.presentation.utils.ColorPalette
import com.example.quizapp.ui.theme.Lexend

@Preview
@Composable
fun FirstScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorPalette.bgDark)
    ) {
        WelcomeBar()

        Spacer(modifier = Modifier.height(30.dp))

        QuizColumn()
    }
}

@Composable
fun WelcomeBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            " Welcome Back, Alex!", fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        IconButton(
            onClick = {

            }
        ) {
            Icon(
                painter = painterResource(R.drawable.setting),
                contentDescription = "Settings icon",
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
        }
    }
}


@Composable
fun QuizColumn() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(fraction = .9f)
                .height(200.dp)
                .background(
                    ColorPalette.bgInitialCards,
                    shape = RoundedCornerShape(16.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .background(ColorPalette.bgIcon, shape = CircleShape)
                    .padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.video_game),
                    contentDescription = "GamePad Icon",
                    modifier = Modifier.size(32.dp),
                    tint = ColorPalette.primaryGreen
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth(fraction = .8f)
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ColorPalette.primaryGreen
                ),
                onClick = {

                },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "Play Quizzes",
                    style = MaterialTheme.typography.labelLarge
                        .copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Lexend
                        )
                )
            }

            Text("Test your knowledge & challenge friends.", color = Color.White)
        }

        Spacer(Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(fraction = .9f)
                .height(200.dp)
                .background(
                    ColorPalette.bgInitialCards,
                    shape = RoundedCornerShape(16.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .background(ColorPalette.bgIcon, shape = CircleShape)
                    .padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_circle),
                    contentDescription = "GamePad Icon",
                    modifier = Modifier.size(32.dp),
                    tint = ColorPalette.primaryGreen
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth(fraction = .8f)
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ColorPalette.blueButton
                ),
                onClick = {

                },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "Create Quiz",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Lexend
                    )
                )
            }

            Text("Build your own quiz and share it", color = Color.White)
        }
    }
}
