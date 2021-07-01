package com.rsschool.quiz.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.rsschool.quiz.FragmentListener
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentFinishBinding

class ResultFragment : Fragment() {

    private var _binding: FragmentFinishBinding? = null
    private val binding: FragmentFinishBinding
        get() = requireNotNull(_binding)

    private var _fragmentListener: FragmentListener? = null
    private val fragmentListener: FragmentListener
        get() = requireNotNull(_fragmentListener)

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
        _binding = FragmentFinishBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val numQuestions = arguments?.get(NUM_QUESTIONS_KEY)
        val numCorrect = arguments?.get(NUM_CORRECT_KEY)
        val answers = arguments?.get(ANSWERS_KEY) as Array<*>

        with(binding) {
            tvResult.text = String.format(
                getString(R.string.your_result),
                numCorrect, numQuestions
            )

            btnClose.setOnClickListener {
                fragmentListener.finish()
            }

            btnShare.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                with(intent) {
                    type = "text/plain"
                    putExtra(
                        Intent.EXTRA_TEXT, String.format(
                            getString(R.string.my_result),
                            numCorrect, numQuestions
                        ) + "\n\n" + answers.joinToString("\n***\n")
                    )
                    putExtra(Intent.EXTRA_TITLE, getString(R.string.result_share_title))
                }
                startActivity(Intent.createChooser(intent, getString(R.string.result_share_title)))
            }

            btnBack.setOnClickListener {
                fragmentListener.openQuizFragment(-1)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(
            numQuestions: Int,
            numCorrect: Int,
            answers: Array<String>
        ): ResultFragment {
            val fragment = ResultFragment()
            val args = bundleOf(
                NUM_QUESTIONS_KEY to numQuestions,
                NUM_CORRECT_KEY to numCorrect,
                ANSWERS_KEY to answers
            )
            fragment.arguments = args
            return fragment
        }

        private const val NUM_QUESTIONS_KEY = "NUM_QUESTIONS"
        private const val NUM_CORRECT_KEY = "NUM_CORRECT"
        private const val ANSWERS_KEY = "ANSWERS"
    }
}