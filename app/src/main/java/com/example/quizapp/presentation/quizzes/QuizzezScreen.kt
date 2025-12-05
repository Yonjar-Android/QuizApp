package com.example.quizapp.presentation.quizzes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.R
import com.example.quizapp.presentation.utils.ColorPalette
import com.example.quizapp.ui.theme.Lexend

@Preview

@Composable
fun QuizzesScreen() {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ColorPalette.bgDark),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(fraction = .94f)
            ) {
                TopBarIcons()

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Quizzes", color = Color.White, fontFamily = Lexend,
                        fontWeight = FontWeight.Bold, fontSize = 32.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                TabNavigation()

                Spacer(modifier = Modifier.height(20.dp))

                LazyColumn {
                    items(10) {
                        QuizItem()
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = ColorPalette.primaryGreen,
            shape = CircleShape,
            onClick = {

            }
        ) {
            Icon(
                painter = painterResource(R.drawable.plus),
                contentDescription = "Add Icon",
                tint = Color.Black,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun TopBarIcons() {

    var search by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            onClick = {

            }
        ) {
            Icon(
                painter = painterResource(R.drawable.search),
                contentDescription = "Settings icon",
                modifier = Modifier.size(20.dp),
                tint = Color.White
            )
        }
    }
}

@Composable
fun TabNavigation() {

    val tabs = listOf<String>("All", "My Quizzes", "Default")

    val selectedTab = remember { mutableIntStateOf(0) }

    SecondaryTabRow(
        modifier = Modifier.clip(RoundedCornerShape(8.dp)),
        selectedTabIndex = selectedTab.intValue,
        containerColor = ColorPalette.bgInitialCards,
        indicator = {},
        divider = {},
    ) {
        tabs.forEachIndexed { index, value ->
            val isSelected = selectedTab.intValue == index

            val backgroundSelect =
                if (isSelected) ColorPalette.blueButton else ColorPalette.bgInitialCards

            Tab(
                modifier = Modifier
                    .height(40.dp)
                    .padding(4.dp)
                    .background(
                        backgroundSelect,
                        shape = RoundedCornerShape(8.dp)
                    ),
                selectedContentColor = Color.Transparent,
                selected = isSelected,
                onClick = {
                    selectedTab.intValue = index
                },
                text = {
                    Text(
                        text = value,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontFamily = Lexend
                    )
                }
            )
        }
    }
}

@Composable
fun QuizItem() {
    Row(
        Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(
                ColorPalette.bgQuizCards,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .weight(2f)
                .padding(16.dp)
        ) {
            Text(
                "Geography", color = ColorPalette.primaryGreen,
                fontFamily = Lexend,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                "World Capitals",
                color = Color.White,
                fontFamily = Lexend,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                "10 Questions ° 5 min",
                color = Color.Gray,
                fontFamily = Lexend,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = ColorPalette.greenButton
                )
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Start",
                        color = ColorPalette.primaryGreen,
                        fontFamily = Lexend,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        painter = painterResource(R.drawable.play),
                        contentDescription = "Arrow Back",
                        tint = ColorPalette.primaryGreen,
                        modifier = Modifier.size(10.dp)
                    )
                }
            }
        }

            Image(
                modifier = Modifier
                    .weight(1.3f)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    ,
                painter = painterResource(R.drawable.roma),
                contentDescription = "World Map",
                contentScale = ContentScale.Crop
            )


    }
}