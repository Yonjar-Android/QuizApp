package com.example.quizapp.presentation.classes

import java.util.UUID

data class QuestionModel(
    val id: Long,
    val idQuiz: Long,
    val question: String,
    val options: List<OptionModel>,
    val uiId: String = UUID.randomUUID().toString(),
)

data class OptionModel(
    val id: Long,
    val idQuestion: Long,
    val answer: String,
    var isCorrect: Boolean
)
