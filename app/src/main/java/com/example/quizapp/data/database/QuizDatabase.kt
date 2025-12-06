package com.example.quizapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quizapp.data.database.daos.OptionDao
import com.example.quizapp.data.database.daos.QuestionDao
import com.example.quizapp.data.database.daos.QuizDao
import com.example.quizapp.data.database.entities.OptionEntity
import com.example.quizapp.data.database.entities.QuestionEntity
import com.example.quizapp.data.database.entities.QuizEntity

@Database(
    entities = [
        QuizEntity::class,
        QuestionEntity::class,
        OptionEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class QuizDatabase : RoomDatabase() {

    abstract fun quizDao(): QuizDao
    abstract fun questionDao(): QuestionDao
    abstract fun optionDao(): OptionDao

    companion object {
        fun getDatabase(context: Context): QuizDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                QuizDatabase::class.java,
                "quiz_database"
            ).build()
        }
    }


}