package com.example.quizapp.presentation.myQuizzes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.repositories.QuizRepository
import com.example.quizapp.presentation.classes.QuizModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MyQuizzesViewModel(
    private val quizRepository: QuizRepository
): ViewModel() {
    val quiz: StateFlow<List<QuizModel>> = quizRepository.getAllQuizzes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}