package com.example.quizapp.presentation.myQuizzes

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.quizapp.R
import com.example.quizapp.presentation.NavigationItem
import com.example.quizapp.presentation.classes.QuizModel
import com.example.quizapp.presentation.utils.ColorPalette
import com.example.quizapp.ui.theme.Lexend
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyQuizzesScreen(
    controller: NavHostController,
    viewModel: MyQuizzesViewModel = koinViewModel()
) {

    val search by viewModel.query.collectAsStateWithLifecycle()

    val quiz by viewModel.searchResults.collectAsStateWithLifecycle()

    val message by viewModel.message.collectAsStateWithLifecycle()

    var deleteQuiz by remember { mutableStateOf(false) }

    var quizModel by remember { mutableStateOf<QuizModel?>(null) }


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ColorPalette.bgDark),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(fraction = 0.95f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = {
                            controller.navigateUp()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.arrow),
                            contentDescription = "Close Icon",
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "My Quizzes", color = Color.White,
                        fontFamily = Lexend,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 32.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            color = ColorPalette.borderTextField,
                            RoundedCornerShape(8.dp)
                        ),

                    value = search, onValueChange = { viewModel.onQueryChange(it) },
                    placeholder = {
                        Text(
                            "Search for a quiz",
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

                Spacer(modifier = Modifier.height(20.dp))

                LazyColumn() {
                    items(quiz) { quizInfo ->
                        MyQuizItem(
                            quizInfo,
                            navigateToQuiz = { quizId ->
                                controller.navigate("${NavigationItem.QUESTION.route}/$quizId")
                            },
                            showDeleteQuiz = { quizToDelete ->
                                quizModel = quizToDelete
                                deleteQuiz = true
                            },
                            navigateToEdit = {
                                controller.navigate("${NavigationItem.CREATE_QUIZ.route}/${quizInfo.id}")
                            }
                        )
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
                // Add a quiz
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

        if (deleteQuiz) {
            DeleteCard(
                deleteQuiz = {
                    if (quizModel != null) {
                        viewModel.deleteQuiz(quizModel!!)
                        deleteQuiz = false
                    } else {
                        deleteQuiz = false
                    }
                },
                closeCard = {
                    deleteQuiz = false
                },
                quizTitle = quizModel?.title ?: ""
            )
        }
    }
}

@Composable
fun MyQuizItem(
    quiz: QuizModel,
    navigateToQuiz: (Long) -> Unit,
    showDeleteQuiz: (QuizModel) -> Unit = {},
    navigateToEdit: () -> Unit = {}
) {

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = ColorPalette.bgQuizCards,
                shape = RoundedCornerShape(8.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(fraction = 0.9f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    quiz.title,
                    color = Color.White,
                    fontFamily = Lexend,
                    fontWeight = FontWeight.Bold
                )

                Box {
                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = {
                            expanded = true
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.more),
                            contentDescription = "More Icon",
                            tint = Color.White
                        )
                    }
                    // DropDownMenu
                    DropDownMenuQuiz(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        showDeleteQuiz = {
                            showDeleteQuiz.invoke(quiz)
                            expanded = false
                        },
                        navigateToEdit = {
                            navigateToEdit.invoke()
                            expanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                "${quiz.questionCount} Questions ° 3 min",
                color = Color.Gray,
                fontFamily = Lexend,
                fontWeight = FontWeight.Normal
            )
        }
        Spacer(modifier = Modifier.height(4.dp))

        Button(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth(fraction = 0.9f),
            onClick = {
                navigateToQuiz.invoke(
                    quiz.id
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = ColorPalette.primaryGreen
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.play),
                contentDescription = "Play Icon",
                tint = Color.Black,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun DropDownMenuQuiz(
    expanded: Boolean = false,
    onDismissRequest: () -> Unit = {},
    showDeleteQuiz: () -> Unit = {},
    navigateToEdit: () -> Unit = {}
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {

        DropdownMenuItem(
            text = { Text("Edit") },
            onClick = { navigateToEdit.invoke() }
        )

        DropdownMenuItem(
            text = { Text("Delete") },
            onClick = { showDeleteQuiz.invoke() }
        )

    }
}

@Composable
fun DeleteCard(
    quizTitle: String,
    deleteQuiz: () -> Unit ,
    closeCard: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.9f)
                .background(
                    ColorPalette.bgDeleteCard,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                "Delete Quiz",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Lexend
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(modifier = Modifier.fillMaxWidth(fraction = 0.95f),
                text = """Are you sure you want to delete "$quizTitle"? This action cannot be undone.
                """.trimMargin(),
                color = Color.Gray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = Lexend,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            LastButtons(
                deleteQuiz = {
                    deleteQuiz.invoke()
                },
                closeCard = {
                    closeCard.invoke()
                }
            )
        }
    }
}

@Composable
fun LastButtons(
    deleteQuiz: () -> Unit = {},
    closeCard: () -> Unit = {}
) {

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ColorPalette.deleteButtonColor
        ),
        onClick = {
            deleteQuiz.invoke()
        },
        shape = RoundedCornerShape(8.dp)
    ) {

        Text(
            "Yes, Delete",
            style = MaterialTheme.typography.labelLarge
                .copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Lexend,
                    fontSize = 18.sp
                )
        )
    }

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ColorPalette.cancelButtonColor
        ),
        onClick = {
            closeCard.invoke()
        },
        shape = RoundedCornerShape(8.dp)
    ) {


        Text(
            "Cancel",
            style = MaterialTheme.typography.labelLarge
                .copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Lexend,
                    fontSize = 18.sp
                )
        )
    }
}