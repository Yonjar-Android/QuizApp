package com.example.quizapp.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "question",
    foreignKeys = [
        ForeignKey(
            entity = QuizEntity::class,
            parentColumns = ["id"],
            childColumns = ["idQuiz"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val idQuiz: Long,
    val question: String,
    )
