package com.example.quizapp.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // Crear índice en QuestionEntity
            db.execSQL("CREATE INDEX index_question_idQuiz ON question(idQuiz)")

            // Crear índice en OptionEntity
            db.execSQL("CREATE INDEX index_option_idQuestion ON `option`(idQuestion)")
        }
    }
}