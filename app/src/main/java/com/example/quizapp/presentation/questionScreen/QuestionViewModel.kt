package com.example.quizapp.presentation.questionScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.repositories.QuizRepository
import com.example.quizapp.presentation.classes.QuizModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuestionViewModel(
    private val quizRepository: QuizRepository
): ViewModel() {

    private var _quiz = MutableStateFlow<QuizModel?>(null)
    val quiz = _quiz.asStateFlow()

    private var _currentIndex = MutableStateFlow(0)
    val currentIndex = _currentIndex.asStateFlow()

    private val _selectedOptions = MutableStateFlow<Map<Long, Long>>(emptyMap())
    val selectedOptions: StateFlow<Map<Long, Long>> = _selectedOptions

    fun loadQuestions(quizId: Long){
        viewModelScope.launch {
           _quiz.value = quizRepository.getQuizById(quizId)
            _currentIndex.value = 0
            _selectedOptions.value = emptyMap()
        }
    }

    fun nextQuestion() {
        _currentIndex.value = (_currentIndex.value + 1).coerceAtMost((_quiz.value?.questions?.size ?: 1) - 1)
    }

    fun previousQuestion() {
        _currentIndex.value = (_currentIndex.value - 1).coerceAtLeast(0)
    }


    fun selectOption(questionId: Long, optionId: Long) {
        _selectedOptions.value = _selectedOptions.value.toMutableMap().apply {
            this[questionId] = optionId
        }
    }

    fun getSelectedOption(questionId: Long): Long? {
        return _selectedOptions.value[questionId]
    }

    fun sumOfCorrectAnswers(): Int {
        var correctAnswers = 0

        _quiz.value?.questions?.forEach { question ->
            val selectedOptionId = _selectedOptions.value[question.id]
            val correctOptionId = question.options.find { it.isCorrect }?.id

            if (selectedOptionId == correctOptionId) {
                correctAnswers++
            }
        }

        return correctAnswers
    }

    // Measure time functions

    private var startTime = 0L

    fun startTimer() {
        startTime = System.currentTimeMillis()
    }

    fun stopTimer(): Long {
        val endTime = System.currentTimeMillis()
        return endTime - startTime  // duration in ms
    }

}