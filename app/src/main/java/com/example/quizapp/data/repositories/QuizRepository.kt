package com.example.quizapp.data.repositories

import androidx.room.withTransaction
import com.example.quizapp.data.database.QuizDatabase
import com.example.quizapp.data.database.daos.OptionDao
import com.example.quizapp.data.database.daos.QuestionDao
import com.example.quizapp.data.database.daos.QuizDao
import com.example.quizapp.data.database.entities.OptionEntity
import com.example.quizapp.data.database.entities.QuestionEntity
import com.example.quizapp.data.database.entities.QuizEntity
import com.example.quizapp.data.mappers.toModel
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
        return quizDao.getAllQuizzesWithQuestionCount().map { it ->
            it.map {
                QuizModel(
                    id = it.quiz.id,
                    title = it.quiz.title,
                    category = it.quiz.category,
                    isDefault = it.quiz.isDefault,
                    questionCount = it.questionCount
                )
            }
        }
    }

    suspend fun getQuizById(quizId: Long): QuizModel {
        val response = quizDao.getQuizWithQuestions(quizId)
        println(response)
        return QuizModel(
            id = response.quiz.id,
            title = response.quiz.title,
            category = response.quiz.category,
            questions = response.questions.map {
                it.toModel()
            }
        )
    }

    fun searchQuizzes(query: String): Flow<List<QuizModel>> =
        quizDao.searchQuizWithQuestionCount(query).map { list ->
            list.map { entity ->
                QuizModel(
                    id = entity.quiz.id,
                    title = entity.quiz.title,
                    category = entity.quiz.category,
                    isDefault = entity.quiz.isDefault,
                    questionCount = entity.questionCount
                )
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

    suspend fun updateQuiz(
        quizEntity: QuizEntity,
        question: List<QuestionModel>,
        originalIdQuestions: List<Long>
    ){
        database.withTransaction {
            // Update quiz entity
            quizDao.updateQuiz(quizEntity)

            // Insert or update questions
            question.forEach { q ->
                if (q.id == 0L) {
                    // New question → insert
                   val newQuestionId = questionDao.insertQuestion(
                        QuestionEntity(
                            idQuiz = q.idQuiz,
                            question = q.question))

                    optionDao.insertOptions(
                        q.options.map {
                            OptionEntity(
                                idQuestion = newQuestionId,
                                answer = it.answer,
                                isCorrect = it.isCorrect
                            )
                        }
                    )
                } else {
                    // Existing question → update
                    questionDao.updateQuestion(
                        QuestionEntity(
                            id = q.id,
                            idQuiz = q.idQuiz,
                            question = q.question
                        )
                    )
                }
            }

            // 3️⃣ Delete questions that are in originalIdQuestions but not in the updated list
            val updatedIds = question.map { it.id }.filter { it != 0L }.toSet()
            val deletedIds = originalIdQuestions.filter { it !in updatedIds }

            deletedIds.forEach { id ->
                questionDao.deleteQuestionById(id)
            }

        }
    }

    suspend fun deleteQuiz(quiz: QuizEntity): Int {
        return database.withTransaction {
            quizDao.deleteQuiz(quiz)
        }
    }
}