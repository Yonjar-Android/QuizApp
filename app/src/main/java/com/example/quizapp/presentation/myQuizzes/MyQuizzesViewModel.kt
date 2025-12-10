package com.example.quizapp.presentation.myQuizzes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.database.entities.QuizEntity
import com.example.quizapp.data.repositories.QuizRepository
import com.example.quizapp.presentation.classes.QuizModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class MyQuizzesViewModel(
    private val quizRepository: QuizRepository
) : ViewModel() {

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResults: StateFlow<List<QuizModel>> =
        merge(
            _query.take(1).flatMapLatest { text ->
                quizRepository.searchQuizzes(text)
            },
            _query
                .debounce(700) // Wait 0.7 seconds after typing
                .distinctUntilChanged()
                .flatMapLatest { text ->
                    quizRepository.searchQuizzes(text)
                }
        )
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    fun onQueryChange(newValue: String) {
        _query.value = newValue
    }

    fun deleteQuiz(quiz: QuizModel){
        viewModelScope.launch {
           val response = quizRepository.deleteQuiz(
                QuizEntity(
                    id = quiz.id,
                    title = quiz.title,
                    category = quiz.category,
            ))

            if(response > 0){
                _message.value = "Quiz deleted"
            }else{
                _message.value = "Quiz not deleted"
            }
        }

    }
}