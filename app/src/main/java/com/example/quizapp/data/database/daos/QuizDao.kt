package com.example.quizapp.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.quizapp.data.database.entities.QuizEntity
import com.example.quizapp.data.database.relations.QuizWithQuestionCount
import com.example.quizapp.data.database.relations.QuizWithQuestions
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {

    @Query("SELECT * FROM quiz")
    fun getAllQuizzes(): Flow<List<QuizEntity>>

    @Query("""
        SELECT quiz.*, COUNT(question.id) AS questionCount
        FROM quiz
        LEFT JOIN question ON quiz.id = question.idQuiz
        GROUP BY quiz.id
    """)
    fun getAllQuizzesWithQuestionCount(): Flow<List<QuizWithQuestionCount>>

    @Query("""
    SELECT quiz.*, COUNT(question.id) AS questionCount
    FROM quiz
    LEFT JOIN question ON quiz.id = question.idQuiz
    WHERE quiz.title LIKE '%' || :query || '%'
      AND quiz.isDefault = 0
    GROUP BY quiz.id
""")
    fun searchQuizWithQuestionCount(query: String): Flow<List<QuizWithQuestionCount>>

    @Transaction
    @Query("SELECT * FROM quiz WHERE id = :quizId")
    suspend fun getQuizWithQuestions(quizId: Long): QuizWithQuestions

    @Insert
    suspend fun insertQuiz(quiz: QuizEntity): Long

    @Update
    suspend fun updateQuiz(quiz: QuizEntity)

    @Delete
    suspend fun deleteQuiz(quiz: QuizEntity): Int

}