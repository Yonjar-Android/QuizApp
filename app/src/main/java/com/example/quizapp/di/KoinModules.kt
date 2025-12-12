package com.example.quizapp.di

import com.example.quizapp.data.database.QuizDatabase
import com.example.quizapp.data.repositories.QuizRepository
import com.example.quizapp.presentation.createQuiz.CreateQuizViewModel
import com.example.quizapp.presentation.myQuizzes.MyQuizzesViewModel
import com.example.quizapp.presentation.questionScreen.QuestionViewModel
import com.example.quizapp.presentation.quizzes.QuizzesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {

    // Database single instance
    single<QuizDatabase> { QuizDatabase.getDatabase(get()) }

    // QuizDao single instance
    single{ get<QuizDatabase>().quizDao() }

    // QuestionDao single instance
    single{ get<QuizDatabase>().questionDao() }

    // OptionDao single instance
    single{ get<QuizDatabase>().optionDao() }

}

// Inject repository and its dependencies
val repositoryModule = module {
    single {
        QuizRepository(
            quizDao = get(),
            questionDao = get(),
            optionDao = get(),
            database = get()
        )
    }
}

// Inject view model and its dependencies
val viewModelModule = module{
    viewModel  {
        CreateQuizViewModel(
            quizRepository = get()
        )
    }

    viewModel {
        MyQuizzesViewModel(
            quizRepository = get()
        )
    }

    viewModel {
        QuestionViewModel(
            quizRepository = get()
        )
    }

    viewModel {
        QuizzesViewModel(
            quizRepository = get()
        )
    }
}
