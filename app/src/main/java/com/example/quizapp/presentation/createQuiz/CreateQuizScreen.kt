package com.example.quizapp.presentation.createQuiz

import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.quizapp.R
import com.example.quizapp.data.database.entities.QuizEntity
import com.example.quizapp.presentation.classes.OptionModel
import com.example.quizapp.presentation.classes.QuestionModel
import com.example.quizapp.presentation.utils.ColorPalette
import com.example.quizapp.ui.theme.Lexend
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateQuizScreen(
    mainContext: Context,
    controller: NavHostController,
    viewModel: CreateQuizViewModel = koinViewModel(),
    quizId: Long? = null
) {

    var title by remember { mutableStateOf("") }

    var category by remember { mutableStateOf("") }

    val message by viewModel.message.collectAsStateWithLifecycle()

    val quizToEdit by viewModel.quiz.collectAsStateWithLifecycle()

    val questions = remember { mutableStateListOf<QuestionModel>() }

    if (message.isNotEmpty()) {
        Toast.makeText(mainContext, message, Toast.LENGTH_SHORT).show()

        if (message == "Quiz created successfully" || message == "Quiz updated successfully") {
            controller.navigateUp()
            viewModel.resetMessage()
        }
    }

    LaunchedEffect(quizId) {
        if (quizId != null) {
            viewModel.loadQuizByiD(quizId)
        }
    }

    LaunchedEffect(quizToEdit) {
        quizToEdit?.let { quiz ->
            title = quiz.title
            category = quiz.category

            questions.clear()
            questions.addAll(quiz.questions)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ColorPalette.bgDark),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(fraction = 0.94f),
            ) {
                TopBarIcons(
                    controller,
                    createQuiz = {

                        viewModel.createQuiz(
                            QuizEntity(
                                id = quizId ?: 0,
                                title = title,
                                category = category
                            ),
                            questions
                        )
                    })

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

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    "Category", color = Color.White,
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

                    value = category, onValueChange = { category = it },
                    placeholder = {
                        Text(
                            "Enter a category for your quiz",
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

                LazyColumn {
                    itemsIndexed(
                        questions,
                        key = { _, question -> question.uiId }
                    ) { i, q ->

                        QuestionItem(
                            modifier = Modifier.animateItem(),
                            questionInfo = q,

                            onQuestionChanged = { newText ->
                                questions[i] = questions[i].copy(question = newText)
                            },

                            onOptionSelected = { optionIndex ->
                                questions[i] = questions[i].copy(
                                    options = q.options.mapIndexed { idx, opt ->
                                        opt.copy(isCorrect = idx == optionIndex)
                                    }
                                )
                            },

                            onOptionChanged = { optionIndex, newText ->
                                questions[i] = questions[i].copy(
                                    options = q.options.mapIndexed { idx, opt ->
                                        if (idx == optionIndex) opt.copy(answer = newText)
                                        else opt
                                    }
                                )
                            },

                            onDelete = {
                                questions.removeAt(i)
                            }
                        )

                        Spacer(Modifier.height(15.dp))
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
                // Add a question
                questions.add(
                    QuestionModel(
                        id = 0,
                        idQuiz = 0,
                        question = "",
                        options = listOf(
                            OptionModel(
                                id = 0,
                                idQuestion = 0,
                                answer = "",
                                isCorrect = true
                            ),
                            OptionModel(
                                id = 0,
                                idQuestion = 0,
                                answer = "",
                                isCorrect = false
                            ),
                            OptionModel(
                                id = 0,
                                idQuestion = 0,
                                answer = "",
                                isCorrect = false
                            ),
                            OptionModel(
                                id = 0,
                                idQuestion = 0,
                                answer = "",
                                isCorrect = false
                            )
                        )
                    )
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
fun TopBarIcons(
    controller: NavHostController,
    createQuiz: () -> Unit
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
            onClick = {
                createQuiz.invoke()
            }
        ) {
            Text(
                "Save", color = ColorPalette.primaryGreen, fontWeight = FontWeight.Bold,
                fontFamily = Lexend, fontSize = 18.sp
            )
        }
    }
}

@Composable
fun QuestionItem(
    modifier: Modifier,
    questionInfo: QuestionModel,
    onQuestionChanged: (String) -> Unit,
    onOptionSelected: (Int) -> Unit,
    onOptionChanged: (Int, String) -> Unit,
    onDelete: () -> Unit
) {

    Column(
        modifier = modifier
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
                "Question",
                color = Color.White,
                fontFamily = Lexend,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )

            IconButton(
                modifier = Modifier.size(22.dp),
                onClick = onDelete
            ) {
                Icon(
                    painter = painterResource(R.drawable.delete),
                    contentDescription = "Delete Icon",
                    tint = Color.White
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

            value = questionInfo.question,
            onValueChange = onQuestionChanged,
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

        questionInfo.options.forEachIndexed { index, option ->
            AnswerItem(
                answerInfo = option,
                selected = option.isCorrect,
                onSelected = { onOptionSelected(index) },
                onAnswerChanged = { text -> onOptionChanged(index, text) }
            )
        }
    }
}


@Composable
fun AnswerItem(
    answerInfo: OptionModel,
    selected: Boolean,
    onSelected: () -> Unit,
    onAnswerChanged: (String) -> Unit
) {

    val colorSelected =
        if (selected) ColorPalette.primaryGreen
        else ColorPalette.borderTextField

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        RadioButton(
            modifier = Modifier.weight(1f),
            selected = selected,
            onClick = onSelected,
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

            value = answerInfo.answer,
            onValueChange = onAnswerChanged,

            placeholder = {
                Text(
                    "Answer",
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