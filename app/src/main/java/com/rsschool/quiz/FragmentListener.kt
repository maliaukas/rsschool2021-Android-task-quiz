package com.rsschool.quiz

interface FragmentListener {
    fun openQuizFragment(currQuestionIdx: Int)
    fun openFinishFragment(numQuestions: Int, numCorrect: Int, answers: Array<String>)

    fun nextQuestion()
    fun prevQuestion()

    fun finish()
}