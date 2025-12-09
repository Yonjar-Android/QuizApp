package com.example.quizapp.data.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.quizapp.data.database.entities.OptionEntity
import com.example.quizapp.data.database.entities.QuestionEntity

data class QuestionWithOptions(
    @Embedded val question: QuestionEntity,
    @Relation(
        entity = OptionEntity::class,
        parentColumn = "id",
        entityColumn = "idQuestion"
    )
    val options: List<OptionEntity>
)