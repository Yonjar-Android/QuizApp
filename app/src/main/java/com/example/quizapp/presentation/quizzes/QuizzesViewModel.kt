package com.example.quizapp.presentation.quizzes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.repositories.QuizRepository
import com.example.quizapp.presentation.classes.QuizModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class QuizzesViewModel(
    private val quizRepository: QuizRepository
): ViewModel() {

    val quizzes: StateFlow<List<QuizModel>> = quizRepository.getAllQuizzes()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

}