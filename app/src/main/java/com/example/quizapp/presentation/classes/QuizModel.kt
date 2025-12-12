package com.example.quizapp.presentation.classes

data class QuizModel(
    val id: Long,
    val title: String,
    val category: String,
    val isDefault: Boolean = false,
    val questions: List<QuestionModel> = emptyList()
)
