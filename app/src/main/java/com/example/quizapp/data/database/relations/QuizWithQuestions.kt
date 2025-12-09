package com.example.quizapp.data.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.quizapp.data.database.entities.QuestionEntity
import com.example.quizapp.data.database.entities.QuizEntity

data class QuizWithQuestions(
    @Embedded val quiz: QuizEntity,
    @Relation(
        entity = QuestionEntity::class,
        parentColumn = "id",
        entityColumn = "idQuiz"
    )
    val questions: List<QuestionWithOptions>
)