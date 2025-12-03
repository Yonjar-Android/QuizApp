package com.example.quizapp.presentation

import com.example.quizapp.R

enum class NavigationItem(
    val title: String,
    val icon: Int,
    val route: String
) {
    HOME(title = "Home", icon = R.drawable.home, route = "firstScreen"),
    QUIZZES(title = "My Quizzes", icon = R.drawable.list_view, route = "secondScreen"),
    LEADERBOARD(title = "Leaderboard", icon = R.drawable.leaderboard, route = "thirdScreen"),
    PROFILE(title = "Profile", icon = R.drawable.user, route = "fourthScreen")


}