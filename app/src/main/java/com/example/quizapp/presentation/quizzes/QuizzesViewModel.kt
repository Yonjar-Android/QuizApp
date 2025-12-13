package com.example.quizapp.presentation.quizzes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.repositories.QuizRepository
import com.example.quizapp.presentation.classes.QuizModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class QuizzesViewModel(
    private val quizRepository: QuizRepository
): ViewModel() {

    private val filter = MutableStateFlow(QuizFilter.ALL)

    private val quizzesFlow = quizRepository.getAllQuizzes()

    val quizzes: StateFlow<List<QuizModel>> =
        combine(quizzesFlow, filter){ quizzes, filter ->
            when(filter){
                QuizFilter.ALL -> quizzes
                QuizFilter.DEFAULT -> quizzes.filter { it.isDefault }
                QuizFilter.USER -> quizzes.filter { !it.isDefault }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun setFilter(type: QuizFilter){
        filter.value = type
    }

}

enum class QuizFilter { ALL, DEFAULT, USER }
