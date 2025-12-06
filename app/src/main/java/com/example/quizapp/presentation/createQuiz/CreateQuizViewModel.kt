package com.example.quizapp.presentation.createQuiz

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.database.entities.QuizEntity
import com.example.quizapp.data.repositories.QuizRepository
import com.example.quizapp.presentation.classes.QuestionModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class CreateQuizViewModel(
    private val quizRepository: QuizRepository
): ViewModel() {

    private var _message = MutableStateFlow<String>("")
    val message = _message.asStateFlow()

    fun createQuiz(
        quizEntity: QuizEntity,
        question: List<QuestionModel>
    ){
        viewModelScope.launch {

            val response = quizRepository.createQuiz(quizEntity, question)

            if(response > 0){
                _message.value = "Quiz created successfully"
            }else{
                _message.value = "Quiz creation failed"
            }
        }
    }

    fun resetMessage(){
        _message.value = ""
    }
}