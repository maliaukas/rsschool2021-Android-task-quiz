package com.rsschool.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentFinishBinding

class FinishFragment : Fragment() {

    private lateinit var binding: FragmentFinishBinding
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
        binding = FragmentFinishBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val numQuestions = arguments?.get(NUM_QUESTIONS_KEY)
        val numCorrect = arguments?.get(NUM_CORRECT_KEY)
        val answers = arguments?.get(ANSWERS_KEY) as Array<String>
        with(binding) {
            tvResult.text = String.format(
                getString(R.string.result),
                numCorrect, numQuestions
            )

            btnClose.setOnClickListener {
                fragmentListener.finish()
            }

            btnShare.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                with(intent) {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, answers.joinToString("***\n"))
                    putExtra(Intent.EXTRA_TITLE, "My result in quiz!")
                }
                startActivity(Intent.createChooser(intent, "My result in quiz!"));
            }

            btnBack.setOnClickListener {
                fragmentListener.openQuizFragment()
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(
            numQuestions: Int,
            numCorrect: Int,
            answers: Array<String>
        ): FinishFragment {
            val fragment = FinishFragment()
            val args = Bundle()
            args.putInt(NUM_QUESTIONS_KEY, numQuestions)
            args.putInt(NUM_CORRECT_KEY, numCorrect)
            args.putStringArray(ANSWERS_KEY, answers)
            fragment.arguments = args
            return fragment
        }

        private const val NUM_QUESTIONS_KEY = "NUM_QUESTIONS"
        private const val NUM_CORRECT_KEY = "NUM_CORRECT"
        private const val ANSWERS_KEY = "ANSWERS"
    }
}