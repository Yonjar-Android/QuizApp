package com.example.quizapp.presentation.createQuiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.database.entities.QuizEntity
import com.example.quizapp.data.repositories.QuizRepository
import com.example.quizapp.presentation.classes.QuestionModel
import com.example.quizapp.presentation.classes.QuizModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CreateQuizViewModel(
    private val quizRepository: QuizRepository
) : ViewModel() {

    private val _quiz = MutableStateFlow<QuizModel?>(null)
    val quiz = _quiz.asStateFlow()

    val questionIds = mutableListOf<Long>()

    private var _message = MutableStateFlow<String>("")
    val message = _message.asStateFlow()

    fun loadQuizByiD(quizId: Long) {
        viewModelScope.launch {
            quizRepository.getQuizById(quizId).collectLatest { quiz ->
                _quiz.value = quiz
            }
        }
    }

    fun createQuiz(
        quizEntity: QuizEntity,
        question: List<QuestionModel>
    ) {
        getQuestionIds()
        viewModelScope.launch {
            if (_quiz.value != null) {
                quizRepository.updateQuiz(quizEntity, question, questionIds)
                _message.value = "Quiz updated successfully"
            } else {
                val response = quizRepository.createQuiz(quizEntity, question)

                if (response > 0) {
                    _message.value = "Quiz created successfully"
                } else {
                    _message.value = "Quiz creation failed"
                }
            }


        }
    }

    fun getQuestionIds() {
        if (_quiz.value != null) {
            _quiz.value!!.questions.forEach {
                questionIds.add(it.id)
            }
        }
    }

    fun resetMessage() {
        _message.value = ""
    }
}