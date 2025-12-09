package com.example.quizapp.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.quizapp.data.database.entities.QuizEntity
import com.example.quizapp.data.database.relations.QuizWithQuestions
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {

    @Query("SELECT * FROM quiz")
    fun getAllQuizzes(): Flow<List<QuizEntity>>

    @Transaction
    @Query("SELECT * FROM quiz WHERE id = :quizId")
    suspend fun getQuizWithQuestions(quizId: Long): QuizWithQuestions

    @Insert
    suspend fun insertQuiz(quiz: QuizEntity): Long

    @Update
    suspend fun updateQuiz(quiz: QuizEntity)

    @Delete
    suspend fun deleteQuiz(quiz: QuizEntity)

}