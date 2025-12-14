package com.example.quizapp.presentation.quizzes

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.quizapp.R
import com.example.quizapp.presentation.NavigationItem
import com.example.quizapp.presentation.classes.QuizModel
import com.example.quizapp.presentation.myQuizzes.DropDownMenuQuiz
import com.example.quizapp.presentation.utils.ColorPalette
import com.example.quizapp.ui.theme.Lexend
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuizzesScreen(
    viewModel: QuizzesViewModel = koinViewModel(),
    controller: NavHostController
) {

    val quizzes by viewModel.quizzes.collectAsStateWithLifecycle()

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

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Quizzes", color = Color.White, fontFamily = Lexend,
                        fontWeight = FontWeight.Bold, fontSize = 32.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                TabNavigation(
                    filterQuizzes = { index ->
                        when (index) {
                            1 -> viewModel.setFilter(QuizFilter.USER)
                            2 -> viewModel.setFilter(QuizFilter.DEFAULT)
                            else -> viewModel.setFilter(QuizFilter.ALL)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                LazyColumn {
                    items(quizzes) {

                        QuizItem(
                            it,
                            navigateToQuiz = { quizId ->
                                controller.navigate("${NavigationItem.QUESTION.route}/$quizId")
                            })


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
                controller.navigate(
                    NavigationItem.CREATE_QUIZ.route
                )
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
fun TabNavigation(
    filterQuizzes: (Int) -> Unit
) {

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

            val backgroundSelect by animateColorAsState(
                targetValue = if (isSelected)
                    ColorPalette.blueButton
                else
                    ColorPalette.bgInitialCards,
                label = "tabBackground"
            )

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
                    filterQuizzes.invoke(index)
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
fun QuizItem(
    quizModel: QuizModel,
    navigateToQuiz: (Long) -> Unit = {}
) {
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
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                quizModel.category, color = ColorPalette.primaryGreen,
                fontFamily = Lexend,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                quizModel.title,
                color = Color.White,
                fontFamily = Lexend,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                "${quizModel.questionCount} Questions ° 3 min",
                color = Color.Gray,
                fontFamily = Lexend,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                modifier = Modifier,
                onClick = {
                    navigateToQuiz.invoke(quizModel.id)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ColorPalette.greenButton
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Text(
                        modifier = Modifier,
                        text = "Start",
                        color = ColorPalette.primaryGreen,
                        fontFamily = Lexend,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
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

        val image = when (quizModel.title) {
            "World Capitals" -> R.drawable.roma
            "Pokémon Basics" -> R.drawable.pokemon
            "Football Trivia" -> R.drawable.futbol
            else -> {
                R.drawable.quiz
            }
        }

        Image(
            modifier = Modifier
                .weight(1.3f)
                .padding(16.dp)
                .clip(RoundedCornerShape(8.dp)),
            painter = painterResource(image),
            contentDescription = "World Map",
            contentScale = ContentScale.Crop
        )
    }
}