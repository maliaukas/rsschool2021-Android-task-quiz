package com.rsschool.quiz

interface FragmentListener {
    fun openQuizFragment()
    fun openFinishFragment(numQuestions: Int, numCorrect: Int, answers: Array<String>)
    fun finish()

    fun updateTheme(idx: Int)
}