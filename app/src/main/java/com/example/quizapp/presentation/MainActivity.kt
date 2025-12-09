package com.example.quizapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quizapp.presentation.createQuiz.CreateQuizScreen
import com.example.quizapp.presentation.firstScreen.FirstScreen
import com.example.quizapp.presentation.myQuizzes.MyQuizzesScreen
import com.example.quizapp.presentation.questionScreen.QuestionScreen
import com.example.quizapp.presentation.quizzes.QuizzesScreen
import com.example.quizapp.presentation.utils.ColorPalette
import com.example.quizapp.ui.theme.QuizAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route


            QuizAppTheme {
                Scaffold(
                    bottomBar = {
                        val showBottomBar = currentRoute in listOf(
                            NavigationItem.HOME.route,
                            NavigationItem.ALL_QUIZZES.route
                        )
                        if (showBottomBar) {
                            BottomNavigation(navController)
                        }
                    }
                ) {
                    NavHost(
                        modifier = Modifier.padding(it),
                        navController = navController, startDestination = NavigationItem.HOME.route
                    ) {
                        composable(route = NavigationItem.HOME.route) {
                            FirstScreen(navController)
                        }

                        composable(NavigationItem.ALL_QUIZZES.route) {
                            QuizzesScreen()
                        }

                        composable(route = NavigationItem.CREATE_QUIZ.route) {
                            CreateQuizScreen(
                                controller = navController,
                                mainContext = context
                            )
                        }

                        composable(route = NavigationItem.QUIZZES.route) {
                            MyQuizzesScreen(navController)
                        }

                        composable(
                            route = "${NavigationItem.QUESTION.route}/{quizId}",
                            arguments = listOf(navArgument("quizId", builder = {
                                type = NavType.LongType
                            }))
                        ) { backStackEntry ->
                            val quizId = backStackEntry.arguments?.getLong("quizId")

                            QuestionScreen(
                                quizId = quizId,
                                controller = navController
                            )
                        }


                    }
                    BackHandler {

                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigation(controller: NavHostController) {

    val navigationList = listOf<NavigationItem>(
        NavigationItem.HOME,
        NavigationItem.QUIZZES,
        NavigationItem.LEADERBOARD,
        NavigationItem.PROFILE
    )

    var navigationSelected by remember {
        mutableStateOf(NavigationItem.HOME)
    }
    Column {
        HorizontalDivider()

        NavigationBar(
            containerColor = ColorPalette.bgDark
        ) {
            navigationList.forEach {
                NavigationBarItem(
                    selected = it == navigationSelected,
                    onClick = {
                        navigationSelected = it
                        controller.navigate(it.route)
                    },
                    icon = {
                        it.icon?.let { icon ->
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(icon),
                                contentDescription = it.title,
                            )
                        }

                    },
                    label = {
                        Text(text = it.title)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = ColorPalette.primaryGreen,
                        selectedTextColor = ColorPalette.primaryGreen,
                        indicatorColor = ColorPalette.bgIcon,
                        unselectedIconColor = Color.White,
                        unselectedTextColor = Color.White
                    )
                )

            }
        }
    }
}

