package com.rsschool.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rsschool.quiz.databinding.ActivityMainBinding
import com.rsschool.quiz.quiz.Questions
import com.rsschool.quiz.quiz.QuizFragment
import com.rsschool.quiz.result.ResultFragment

class MainActivity : AppCompatActivity(), FragmentListener {

    private lateinit var binding: ActivityMainBinding

    private val numQuestions = Questions.questions.size
    private var currQuestionIdx = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openQuizFragment(0)
    }

    override fun openQuizFragment(currQuestionIdx: Int) {
        // clear previous answers
        if (currQuestionIdx == -1) {
            Questions.questions.forEach {
                it.userAnswer = -1
            }
            this.currQuestionIdx = 0
            updateTheme(0)
        } else {
            this.currQuestionIdx = currQuestionIdx
        }

        val quizFragment = QuizFragment.newInstance(this.currQuestionIdx)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, quizFragment)
            .commit()
    }

    override fun openFinishFragment(
        numQuestions: Int,
        numCorrect: Int,
        answers: Array<String>
    ) {
        val quizFragment = ResultFragment.newInstance(numQuestions, numCorrect, answers)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, quizFragment)
            .commit()
    }

    override fun nextQuestion() {
        currQuestionIdx++

        // not last question
        if (currQuestionIdx < numQuestions) {
            updateTheme(currQuestionIdx)
            openQuizFragment(currQuestionIdx)
        } // the last question
        else if (currQuestionIdx == numQuestions) {
            finishQuiz()
        }
    }

    private fun finishQuiz() {
        // list of answers that will go to the FinishFragment
        val answersQuestions = mutableListOf<String>()

        // form the answers list
        for ((i, q) in Questions.questions.withIndex()) {
            answersQuestions.add(
                String.format(
                    getString(R.string.share_res),
                    i + 1,
                    String.format(getString(R.string.question), q.question),
                    q.answers[q.userAnswer]
                )
            )
        }

        // evaluate the number of correct answers
        val numCorrect = Questions.questions.count {
            it.rightAnswer == it.userAnswer
        }

        // start the FinishFragment
        openFinishFragment(
            numQuestions,
            numCorrect,
            answersQuestions.toTypedArray()
        )
    }

    override fun prevQuestion() {
        currQuestionIdx--
        updateTheme(currQuestionIdx)
        openQuizFragment(currQuestionIdx)
    }

    private fun updateTheme(idx: Int) {
        when ((idx + 5) % 5) {
            0 -> {
                setTheme(R.style.Theme_Quiz_First)
            }
            1 -> {
                setTheme(R.style.Theme_Quiz_Second)
            }
            2 -> {
                setTheme(R.style.Theme_Quiz_Third)
            }
            3 -> {
                setTheme(R.style.Theme_Quiz_Forth)
            }
            4 -> {
                setTheme(R.style.Theme_Quiz_Fifth)
            }
        }
    }
}