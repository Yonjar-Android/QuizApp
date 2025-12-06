package com.example.quizapp.presentation.classes

data class QuestionModel(
    val id: Long,
    val idQuiz: Long,
    val question: String,
    val options: List<OptionModel>
)

data class OptionModel(
    val id: Long,
    val idQuestion: Long,
    val answer: String,
    var isCorrect: Boolean
)
