package com.shengeliia.everydayenglish.screens.quiz

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.get
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.shengeliia.domain.models.Question
import com.shengeliia.everydayenglish.R
import com.shengeliia.everydayenglish.components.QuizToolbar

class QuizActivity : AppCompatActivity(), QuizContract.ViewMVP {

    private lateinit var presenterMVP: QuizContract.PresenterMVP
    private lateinit var quizToolbar: QuizToolbar
    private lateinit var quizButton: Button
    private lateinit var quizRadioBar: RadioGroup
    private lateinit var quizQuestionText: TextView
    private lateinit var quizLayoutAnimator: ViewAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        presenterMVP = QuizPresenter(intent.getIntExtra(TEST_ID, 0))
        findViews()
        quizButton.setOnClickListener {
            val answer = when(quizRadioBar.checkedRadioButtonId) {
                R.id.quizAnswer1 -> 1
                R.id.quizAnswer2 -> 2
                else -> 3
            }
            presenterMVP.onUserAnswer(answer)
            quizRadioBar.clearCheck()
            quizButton.isEnabled = false
            showOutsideButtonsAnimation()
        }

        quizRadioBar.setOnCheckedChangeListener { _, _ ->  quizButton.isEnabled = true }

        quizToolbar.onBackButtonPressedListener = {
            finish()
        }
    }

    private fun findViews() {
        quizToolbar = findViewById(R.id.quizToolbar)
        quizButton = findViewById(R.id.quizButton)
        quizRadioBar = findViewById(R.id.quizRadioBar)
        quizQuestionText = findViewById(R.id.quizQuestionText)
        quizLayoutAnimator = findViewById(R.id.quizLayoutAnimator)
    }


    override fun onStart() {
        super.onStart()
        presenterMVP.register(this)
    }

    override fun onStop() {
        super.onStop()
        presenterMVP.unregister()
    }

    override fun showPassedAnimation() {
        quizLayoutAnimator.displayedChild = 1
    }

    override fun showFailedAnimation() {
        quizLayoutAnimator.displayedChild = 2
    }

    override fun initLayout(question: Question, currentProgress: List<Boolean>, size: Int) {
        quizToolbar.setQuestionNumber(size)
        quizToolbar.setProgress(currentProgress)
        updateQuestion(question)
    }

    override fun updateQuestion(question: Question) {
        quizQuestionText.text = question.questionText
        setRadioButtonText(0, question.answer1)
        setRadioButtonText(1, question.answer2)
        setRadioButtonText(2, question.answer3)
        showAppearButtonsAnimation()
    }

    private fun setRadioButtonText(child: Int, text: String) {
        val radio = quizRadioBar.getChildAt(child) as RadioButton
        radio.text = text
    }

    override fun updateProgress(isGuessed: Boolean) {
        quizToolbar.addProgress(isGuessed)
    }

    private fun showOutsideButtonsAnimation() {
        val setAnim = AnimatorSet()
        val anim1 = createSideAnimation(ALPHA_INVISIBLE, TRANSLATION_RIGHT, quizRadioBar[0])
        val anim2 = createSideAnimation(ALPHA_INVISIBLE, TRANSLATION_LEFT, quizRadioBar[1])
        val anim3 = createSideAnimation(ALPHA_INVISIBLE, TRANSLATION_RIGHT, quizRadioBar[2])
        setAnim.apply {
            playSequentially(anim1, anim2, anim3)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    presenterMVP.onRequestQuestion()
                }
            })
            duration = DURATION
            start()
        }
    }

    private fun showAppearButtonsAnimation() {
        val setAnim = AnimatorSet()
        val anim1 = createSideAnimation(ALPHA_VISIBLE, TRANSLATION_ZERO, quizRadioBar[0])
        val anim2 = createSideAnimation(ALPHA_VISIBLE, TRANSLATION_ZERO, quizRadioBar[1])
        val anim3 = createSideAnimation(ALPHA_VISIBLE, TRANSLATION_ZERO, quizRadioBar[2])
        setAnim.apply {
            duration = DURATION
            playSequentially(anim1, anim2, anim3)
            start()
        }
    }

    private fun createSideAnimation(alpha: Float, offset: Float, view: View): AnimatorSet {
        val translateAnimation = ObjectAnimator.ofFloat(view, "translationX", offset)
        val fadeAnimation = ObjectAnimator.ofFloat(view, "alpha", alpha)
        return AnimatorSet().apply {
            playTogether(translateAnimation, fadeAnimation)
        }
    }

    companion object {
        const val TEST_ID = "test_id"

        const val TRANSLATION_LEFT = -300f
        const val TRANSLATION_RIGHT = 300f
        const val TRANSLATION_ZERO = 0f
        const val ALPHA_VISIBLE = 1f
        const val ALPHA_INVISIBLE = 0f
        const val DURATION = 200L
    }
}
