package com.example.quizapp.data.repositories

import androidx.room.withTransaction
import com.example.quizapp.data.database.QuizDatabase
import com.example.quizapp.data.database.daos.OptionDao
import com.example.quizapp.data.database.daos.QuestionDao
import com.example.quizapp.data.database.daos.QuizDao
import com.example.quizapp.data.database.entities.OptionEntity
import com.example.quizapp.data.database.entities.QuestionEntity
import com.example.quizapp.data.database.entities.QuizEntity
import com.example.quizapp.presentation.classes.QuestionModel
import com.example.quizapp.presentation.classes.QuizModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuizRepository(
    private val database: QuizDatabase,
    private val quizDao: QuizDao,
    private val questionDao: QuestionDao,
    private val optionDao: OptionDao
) {
    fun getAllQuizzes(): Flow<List<QuizModel>> {
        return quizDao.getAllQuizzes().map { it ->
            it.map {
                QuizModel(
                    id = it.id,
                    title = it.title,
                    category = it.category
                )
            }
        }
    }

    suspend fun createQuiz(
        quizEntity: QuizEntity,
        question: List<QuestionModel>
    ): Long {

        return database.withTransaction {
            // Insert quiz into the database
            val quizId = quizDao.insertQuiz(quizEntity)

            // Insert questions into the database
            question.forEach { model ->

                // Insert question into the database
                val questionId = questionDao.insertQuestion(
                    QuestionEntity(
                        idQuiz = quizId,
                        question = model.question
                    )
                )

                // Insert options into the database
                model.options.forEach {
                    optionDao.insertOption(
                        OptionEntity(
                            idQuestion = questionId,
                            answer = it.answer,
                            isCorrect = it.isCorrect
                        )
                    )
                }

            }

            quizId
        }
    }
}