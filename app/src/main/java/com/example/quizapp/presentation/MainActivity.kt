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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.quizapp.presentation.createQuiz.CreateQuizScreen
import com.example.quizapp.presentation.firstScreen.FirstScreen
import com.example.quizapp.presentation.quizzes.QuizzesScreen
import com.example.quizapp.presentation.utils.ColorPalette
import com.example.quizapp.ui.theme.QuizAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route


            QuizAppTheme {
                Scaffold(
                    bottomBar = {
                        val showBottomBar = currentRoute in listOf(
                            "firstScreen",
                            "secondScreen"
                        )
                        if (showBottomBar){
                            BottomNavigation(navController)
                        }
                    }
                ) {
                    NavHost(
                        modifier = Modifier.padding(it),
                        navController = navController, startDestination = "firstScreen"
                    ) {
                        composable(route = "firstScreen") {
                            FirstScreen(navController)
                        }

                        composable(route = "secondScreen") {
                            QuizzesScreen()
                        }

                        composable(route = "thirdScreen") {
                            CreateQuizScreen(navController)
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
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(it.icon),
                            contentDescription = it.title,
                        )
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

