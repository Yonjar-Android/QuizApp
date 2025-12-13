package com.example.quizapp.data.database.relations

import androidx.room.Embedded
import com.example.quizapp.data.database.entities.QuizEntity

data class QuizWithQuestionCount(
    @Embedded val quiz: QuizEntity,
    val questionCount: Int
)
