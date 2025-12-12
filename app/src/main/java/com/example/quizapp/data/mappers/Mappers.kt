package com.example.quizapp.data.mappers

import com.example.quizapp.data.database.entities.OptionEntity
import com.example.quizapp.data.database.relations.QuestionWithOptions
import com.example.quizapp.data.database.relations.QuizWithQuestions
import com.example.quizapp.presentation.classes.OptionModel
import com.example.quizapp.presentation.classes.QuestionModel
import com.example.quizapp.presentation.classes.QuizModel

fun QuizWithQuestions.toModel() = QuizModel(
    id = quiz.id,
    title = quiz.title,
    category = quiz.category,
    isDefault = quiz.isDefault,
    questions = questions.map { it.toModel() }
)

fun QuestionWithOptions.toModel() = QuestionModel(
    id = question.id,
    idQuiz = question.idQuiz,
    question = question.question,
    options = options.map { it.toModel() }
)

fun OptionEntity.toModel() = OptionModel(
    id = id,
    idQuestion = idQuestion,
    answer = answer,
    isCorrect = isCorrect
)
