package com.example.quizapp.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "option",
    foreignKeys = [
        ForeignKey(
            entity = QuestionEntity::class,
            parentColumns = ["id"],
            childColumns = ["idQuestion"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class OptionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val idQuestion: Long,
    val answer: String,
    val isCorrect: Boolean
    )