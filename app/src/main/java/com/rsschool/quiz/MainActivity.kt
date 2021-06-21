package com.rsschool.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rsschool.quiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FragmentListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openQuizFragment()
    }

    override fun openQuizFragment() {
        Questions.questions.forEach {
            it.userAnswer = -1
        }
        val quizFragment = QuizFragment.newInstance()
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
        val quizFragment = FinishFragment.newInstance(numQuestions, numCorrect, answers)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, quizFragment)
            .commit()
    }

    override fun updateTheme(idx: Int) {
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