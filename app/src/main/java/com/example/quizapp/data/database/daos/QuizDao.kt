package com.example.quizapp.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.quizapp.data.database.entities.QuizEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {

    @Query("SELECT * FROM quiz")
    fun getAllQuizzes(): Flow<List<QuizEntity>>

    @Insert
    suspend fun insertQuiz(quiz: QuizEntity): Long

    @Update
    suspend fun updateQuiz(quiz: QuizEntity)

    @Delete
    suspend fun deleteQuiz(quiz: QuizEntity)

}