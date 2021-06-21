package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {
    private lateinit var binding: FragmentQuizBinding

    private val questionsCount = Questions.questions.size
    private var currentQuestionIdx = 0

    private lateinit var fragmentListener: FragmentListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentListener) {
            fragmentListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            options = listOf(
                optionOne,
                optionTwo,
                optionThree,
                optionFour,
                optionFive
            )

            updateQuestion()

            btnNext.setOnClickListener {
                val checkedRadioButtonId = radioGroup.checkedRadioButtonId
                val checkedRadioButton =
                    radioGroup.findViewById(checkedRadioButtonId) as RadioButton
                val checked = radioGroup.indexOfChild(checkedRadioButton)
                Questions.questions[currentQuestionIdx].userAnswer = checked + 1

                if (currentQuestionIdx < questionsCount - 1) {
                    currentQuestionIdx++
                    updateQuestion()
                } else if (currentQuestionIdx == questionsCount - 1) {
                    val answersQuestions = mutableListOf<String>()
                    for ((i, q) in Questions.questions.withIndex()) {
                        val sb = StringBuilder()
                        sb.append("Question ${i + 1}: ${q.question}\n")
                        sb.append("Your answer: ${q.answers[q.userAnswer]}\n")
                        answersQuestions.add(sb.toString())
                    }

                    val numCorrect = Questions.questions.count {
                        it.rightAnswer == it.userAnswer
                    }

                    fragmentListener.openFinishFragment(
                        questionsCount,
                        numCorrect,
                        answersQuestions.toTypedArray()
                    )
                }
            }

            radioGroup.setOnCheckedChangeListener { _, _ ->
                btnNext.isEnabled = true
            }

            btnPrevious.setOnClickListener {
                if (currentQuestionIdx > 0) {
                    currentQuestionIdx--
                    updateQuestion()
                }
            }

            toolbar.setNavigationOnClickListener {
                if (currentQuestionIdx > 0) {
                    currentQuestionIdx--
                    updateQuestion()
                }
            }
        }

    }

    private lateinit var options: List<RadioButton>

    private fun updateQuestion() {
        fragmentListener.updateTheme(currentQuestionIdx)
        with(binding) {
            toolbar.title = "Question ${currentQuestionIdx + 1}"
            val currQuestion = Questions.questions[currentQuestionIdx]
            tvQuestion.text = currQuestion.question

            for ((idx, ansKey) in currQuestion.answers.keys.withIndex()) {
                options[idx].text = currQuestion.answers[ansKey]
            }

            radioGroup.clearCheck()

            val usAns = currQuestion.userAnswer -1
            if (usAns != -2) {
                options[usAns].isChecked = true

                btnNext.isEnabled = true
            } else {
                btnNext.isEnabled = false
            }

            when (currentQuestionIdx) {
                0 -> {
                    btnPrevious.isEnabled = false
                    toolbar.navigationIcon?.setVisible(false, false)
                }
                questionsCount - 1 -> {
                    btnNext.text = "Submit"
                }
                else -> {
                    btnPrevious.isEnabled = true
                    btnNext.text = "Next"
                    toolbar.navigationIcon?.setVisible(true, false)
                }
            }
        }

    }


    companion object {
        @JvmStatic
        fun newInstance(): QuizFragment {
            return QuizFragment()
        }
    }
}