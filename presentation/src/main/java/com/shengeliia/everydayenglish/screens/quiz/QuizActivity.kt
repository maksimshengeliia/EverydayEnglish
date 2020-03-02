package com.shengeliia.everydayenglish.screens.quiz

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.transition.Scene
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.shengeliia.domain.models.Quiz
import com.shengeliia.everydayenglish.R
import com.shengeliia.everydayenglish.components.QuizToolbar

class QuizActivity : AppCompatActivity(), QuizContract.ViewMVP {

    private lateinit var presenterMVP: QuizContract.PresenterMVP

    private lateinit var quizLayoutPlaceholder: FrameLayout
    private lateinit var mainScene: Scene
    private lateinit var failedScene: Scene
    private lateinit var passedScene: Scene
    private lateinit var transition: Transition

    private lateinit var quizToolbar: QuizToolbar

    private lateinit var quizMainButton: Button
    private lateinit var quizMainRadioBar: RadioGroup
    private lateinit var quizMainQuestion: TextView

    private lateinit var quizPassedButtonBack: Button
    private lateinit var quizPassedButtonAgain: Button
    private lateinit var quizFailedButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        presenterMVP = QuizPresenter(intent.getIntExtra(TEST_ID, 0))
        quizLayoutPlaceholder = findViewById(R.id.quizLayoutPlaceholder)
        quizToolbar = findViewById(R.id.quizToolbar)

        mainScene = Scene.getSceneForLayout(quizLayoutPlaceholder, R.layout.quiz_main_layout, this)
        failedScene = Scene.getSceneForLayout(quizLayoutPlaceholder, R.layout.quiz_failed_layout, this)
        passedScene = Scene.getSceneForLayout(quizLayoutPlaceholder, R.layout.quiz_passed_layout, this)
        transition = TransitionInflater.from(this).inflateTransition(R.transition.fade_transition)
    }

    override fun onStart() {
        super.onStart()
        presenterMVP.register(this)
    }

    override fun onStop() {
        super.onStop()
        presenterMVP.unregister()
    }

    override fun showPassedLayout() {
        applyTransition(passedScene)
        quizPassedButtonBack = passedScene.sceneRoot.findViewById(R.id.quizPassedButton)
        quizPassedButtonBack.setOnClickListener {
            presenterMVP.onExit()
        }

        quizPassedButtonAgain = passedScene.sceneRoot.findViewById(R.id.quizPassedAgain)
        quizPassedButtonAgain.setOnClickListener {
            presenterMVP.onRestartTest()
        }
    }

    override fun showFailedLayout() {
        applyTransition(failedScene)
        quizFailedButton = failedScene.sceneRoot.findViewById(R.id.quizFailedButton)
        quizFailedButton.setOnClickListener {
            presenterMVP.onRestartTest()
        }
    }

    override fun showMainLayout() {
        applyTransition(mainScene)

        quizMainButton = mainScene.sceneRoot.findViewById(R.id.quizMainButton)
        quizMainQuestion = mainScene.sceneRoot.findViewById(R.id.quizMainQuestion)
        quizMainRadioBar = mainScene.sceneRoot.findViewById(R.id.quizMainRadioBar)

        quizMainButton.setOnClickListener {
            val answer = when(quizMainRadioBar.checkedRadioButtonId) {
                R.id.quizAnswer1 -> 1
                R.id.quizAnswer2 -> 2
                else -> 3
            }
            presenterMVP.onUserAnswer(answer)
            quizMainRadioBar.clearCheck()
            quizMainButton.isEnabled = false
            showOutsideButtonsAnimation()
        }

        quizMainRadioBar.setOnCheckedChangeListener { _, _ ->  quizMainButton.isEnabled = true }

        quizToolbar.onBackButtonPressedListener = {
            presenterMVP.onExit()
        }
    }

    private fun applyTransition(scene: Scene) {
        TransitionManager.endTransitions(quizLayoutPlaceholder)
        TransitionManager.go(scene, transition)
    }

    override fun initProgress(currentProgress: List<Boolean>, size: Int) {
        quizToolbar.setQuestionNumber(size)
        quizToolbar.setProgress(currentProgress)
    }

    override fun updateQuestion(quiz: Quiz) {
        quizMainQuestion.text = quiz.question
        setRadioButtonText(0, quiz.answer1)
        setRadioButtonText(1, quiz.answer2)
        setRadioButtonText(2, quiz.answer3)
        showAppearButtonsAnimation()
    }

    private fun setRadioButtonText(child: Int, text: String) {
        val radio = quizMainRadioBar.getChildAt(child) as RadioButton
        radio.text = text
    }

    override fun updateProgress(currentProgress: List<Boolean>) {
        quizToolbar.setProgress(currentProgress)
    }

    override fun showLaunchActivity() {
        finish()
    }

    private fun showOutsideButtonsAnimation() {
        val setAnim = AnimatorSet()
        val anim1 = createSideAnimation(ALPHA_INVISIBLE, TRANSLATION_RIGHT, quizMainRadioBar[0])
        val anim2 = createSideAnimation(ALPHA_INVISIBLE, TRANSLATION_LEFT, quizMainRadioBar[1])
        val anim3 = createSideAnimation(ALPHA_INVISIBLE, TRANSLATION_RIGHT, quizMainRadioBar[2])
        val shadeAnim = ObjectAnimator.ofFloat(quizMainQuestion, "alpha", ALPHA_INVISIBLE)
        shadeAnim.duration = DURATION
        setAnim.apply {
            play(anim1).before(anim2)
            play(anim3).after(anim2)
            play(shadeAnim).with(anim1)
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
        val anim1 = createSideAnimation(ALPHA_VISIBLE, TRANSLATION_ZERO, quizMainRadioBar[0])
        val anim2 = createSideAnimation(ALPHA_VISIBLE, TRANSLATION_ZERO, quizMainRadioBar[1])
        val anim3 = createSideAnimation(ALPHA_VISIBLE, TRANSLATION_ZERO, quizMainRadioBar[2])
        val appearAnim = ObjectAnimator.ofFloat(quizMainQuestion, "alpha", ALPHA_VISIBLE)
        appearAnim.duration = DURATION
        setAnim.apply {
            duration = DURATION
            play(anim1).before(anim2)
            play(anim3).after(anim2)
            play(appearAnim).with(anim1)
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
