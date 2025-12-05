package com.example.quizapp.presentation

import com.example.quizapp.R

enum class NavigationItem(
    val title: String,
    val icon: Int?,
    val route: String
) {
    HOME(title = "Home", icon = R.drawable.home, route = "homeScreen"),
    QUIZZES(title = "My Quizzes", icon = R.drawable.list_view, route = "myQuizzesScreen"),
    LEADERBOARD(title = "Leaderboard", icon = R.drawable.leaderboard, route = "leaderBoardScreen"),
    PROFILE(title = "Profile", icon = R.drawable.user, route = "profileScreen"),

    ALL_QUIZZES(title = "All Quizzes", icon = null, route = "allQuizzesScreen"),
    CREATE_QUIZ(title = "Create Quiz", icon = null, route = "createQuizScreen")

}