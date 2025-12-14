package com.example.quizapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.example.quizapp.presentation.questionScreen.FinalScreen
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
                            NavigationItem.ALL_QUIZZES.route,
                            NavigationItem.QUIZZES.route
                        )
                        if (showBottomBar) {
                            BottomNavigation(navController)
                        }
                    }
                ) {
                    NavHost(
                        modifier = Modifier.padding(it),
                        navController = navController, startDestination = NavigationItem.HOME.route,
                        enterTransition = {
                            fadeIn()
                        },
                        exitTransition = {
                            fadeOut()
                        },
                        popEnterTransition = {
                            fadeIn()
                        },
                        popExitTransition = {
                            fadeOut()
                        }
                    ) {
                        composable(route = NavigationItem.HOME.route) {
                            FirstScreen(navController)
                        }

                        composable(NavigationItem.ALL_QUIZZES.route) {
                            QuizzesScreen(
                                controller = navController
                            )
                        }

                        composable(route = NavigationItem.CREATE_QUIZ.route,
                        ) {
                            CreateQuizScreen(
                                controller = navController,
                                mainContext = context,
                            )
                        }

                        composable(route = "${NavigationItem.CREATE_QUIZ.route}/{quizId}",
                            arguments = listOf(navArgument("quizId", builder = {
                                type = NavType.LongType
                            }))
                        ) { navBackStackEntry ->

                            val quizId = navBackStackEntry.arguments?.getLong("quizId")

                            CreateQuizScreen(
                                controller = navController,
                                mainContext = context,
                                quizId = quizId
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

                        composable(
                            route = "${NavigationItem.FINAL_SCREEN.route}/{totalCorrectAnswers}/{totalQuestions}/{quizId}/{time}",
                            arguments = listOf(
                                navArgument("totalCorrectAnswers", builder = {
                                    type = NavType.IntType
                                }),
                                navArgument("totalQuestions", builder = {
                                    type = NavType.IntType
                                }),
                                navArgument("quizId", builder = {
                                    type = NavType.LongType
                                }),
                                navArgument("time", builder = {
                                    type = NavType.LongType
                                })
                            )
                        ) { backStackEntry ->

                            val totalCorrectAnswers =
                                backStackEntry.arguments?.getInt("totalCorrectAnswers") ?: 0
                            val totalQuestions =
                                backStackEntry.arguments?.getInt("totalQuestions") ?: 0
                            val quizId = backStackEntry.arguments?.getLong("quizId") ?: 0L

                            val finalTime = backStackEntry.arguments?.getLong("time") ?: 0L


                            FinalScreen(
                                controller = navController,
                                quizId = quizId,
                                totalCorrectAnswers = totalCorrectAnswers,
                                totalQuestions = totalQuestions,
                                time = finalTime
                            )
                        }
                    }

                }
                BackHandler { }
            }
        }
    }
}

@Composable
fun BottomNavigation(controller: NavHostController) {

    val navigationList = listOf<NavigationItem>(
        NavigationItem.HOME,
        NavigationItem.QUIZZES,
    )

    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val selectedItem = when (currentRoute) {
        NavigationItem.HOME.route -> NavigationItem.HOME
        NavigationItem.ALL_QUIZZES.route -> NavigationItem.HOME
        NavigationItem.QUIZZES.route -> NavigationItem.QUIZZES
        else -> null
    }

    Column {
        HorizontalDivider()

        NavigationBar(
            containerColor = ColorPalette.bgDark
        ) {
            navigationList.forEach {
                NavigationBarItem(
                    selected = it == selectedItem,
                    onClick = {
                        controller.navigate(it.route) {
                            popUpTo(controller.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
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

