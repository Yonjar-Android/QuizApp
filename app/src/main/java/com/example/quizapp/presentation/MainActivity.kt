package com.example.quizapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizapp.presentation.firstScreen.FirstScreen
import com.example.quizapp.ui.theme.QuizAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            QuizAppTheme {
                Scaffold(
                    bottomBar = {
                        BottomNavigation()
                    }
                ) {
                    NavHost(
                        modifier = Modifier.padding(it),
                        navController = navController, startDestination = "firstScreen"
                    ) {
                        composable(route = "firstScreen") {
                            FirstScreen()
                        }

                        composable(route = "secondScreen") {

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigation(){

    val navigationList = listOf<NavigationItem>(
        NavigationItem.HOME,
        NavigationItem.QUIZZES,
        NavigationItem.LEADERBOARD,
        NavigationItem.PROFILE
    )

    NavigationBar() {
        navigationList.forEach {
            NavigationBarItem(
                selected = false,
                onClick = {

                },
                icon = {
                    Icon(painter = painterResource(it.icon), contentDescription = it.title)
                },
                label = {
                    Text(text = it.title)
                }
            )

        }
    }
}

