package com.rsschool.quiz.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.rsschool.quiz.FragmentListener
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding: FragmentQuizBinding
        get() = requireNotNull(_binding)

    private var _fragmentListener: FragmentListener? = null
    private val fragmentListener: FragmentListener
        get() = requireNotNull(_fragmentListener)

    private var currQuestionIdx: Int = -1
    private val numQuestions = Questions.questions.size

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentListener) {
            _fragmentListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        _fragmentListener = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater)
        return binding.root
    }

    private fun btnNextPrevHandler(v: View) {
        with(binding) {
            // get the index of the selected answer
            val checkedId = radioGroup.checkedRadioButtonId

            // in case something is selected
            if (checkedId != -1) {
                val checkedButton = radioGroup.findViewById(checkedId) as RadioButton
                val checked = radioGroup.indexOfChild(checkedButton)

                // remember the user answer's index
                Questions.questions[currQuestionIdx].userAnswer = checked + 1
            }

            when (v.id) {
                R.id.btnNext -> fragmentListener.nextQuestion()
                R.id.btnPrevious -> fragmentListener.prevQuestion()
                // for toolbar back button
                else -> fragmentListener.prevQuestion()
            }

        }
    }

    private lateinit var options: List<RadioButton>

    private fun initOptions() {
        with(binding) {
            options = listOf(
                optionOne,
                optionTwo,
                optionThree,
                optionFour,
                optionFive
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currQuestionIdx = arguments?.get(CURR_QUESTION_IDX_KEY) as Int

        initOptions()
        updateQuestion()

        with(binding) {
            btnNext.setOnClickListener(::btnNextPrevHandler)
            btnPrevious.setOnClickListener(::btnNextPrevHandler)
            toolbar.setNavigationOnClickListener(::btnNextPrevHandler)

            radioGroup.setOnCheckedChangeListener { _, _ ->
                btnNext.isEnabled = true
            }
        }
    }

    private fun updateQuestion() {
        with(binding) {
            // set toolbar title
            toolbar.title = String.format(getString(R.string.question_title), currQuestionIdx + 1)

            // get current question
            val currQuestion = Questions.questions[currQuestionIdx]

            // set current question text
            tvQuestion.text = String.format(getString(R.string.question), currQuestion.question)

            // set answers for current question
            for ((idx, ansKey) in currQuestion.answers.keys.withIndex()) {
                options[idx].text = currQuestion.answers[ansKey]
            }

            radioGroup.clearCheck()

            // check if user has answered to the current question before
            val usAns = currQuestion.userAnswer - 1
            if (usAns != -2) {
                // set the previous answer
                options[usAns].isChecked = true
                btnNext.isEnabled = true
            } else {
                btnNext.isEnabled = false
            }

            when (currQuestionIdx) {
                // for the first question btnPrevious & toolbar back button are disabled
                0 -> {
                    btnPrevious.isEnabled = false
                    toolbar.navigationIcon = null
                }
                // for the last question btnNext changes it's text to "Submit"
                numQuestions - 1 -> {
                    btnNext.text = getString(R.string.submit)
                }
                // for all the rest questions btnPrevious is enabled,
                // btnNext text is "Next",
                // toolbar back button is visible
                else -> {
                    btnPrevious.isEnabled = true
                    btnNext.text = getString(R.string.next)
                    toolbar.setNavigationIcon(R.drawable.ic_baseline_chevron_left_24)
                }
            }
        }

    }

    companion object {
        private const val CURR_QUESTION_IDX_KEY = "CURR_QUESTION_IDX"

        @JvmStatic
        fun newInstance(currQuestionIdx: Int): QuizFragment {
            val args = bundleOf(
                CURR_QUESTION_IDX_KEY to currQuestionIdx
            )
            val fragment = QuizFragment()
            fragment.arguments = args
            return fragment
        }
    }
}