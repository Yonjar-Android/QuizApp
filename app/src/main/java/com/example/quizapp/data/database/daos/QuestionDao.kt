package com.example.quizapp.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.quizapp.data.database.entities.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {

    @Query("SELECT * FROM question WHERE idQuiz = :idQuiz")
    fun getAllQuestions(idQuiz: Long): Flow<List<QuestionEntity>>

    @Insert
    suspend fun insertQuestion(question: QuestionEntity): Long

    @Update
    suspend fun updateQuestion(question: QuestionEntity)

    @Delete
    suspend fun deleteQuestion(question: QuestionEntity)

    @Query("DELETE FROM question WHERE id = :id")
    suspend fun deleteQuestionById(id: Long)

}