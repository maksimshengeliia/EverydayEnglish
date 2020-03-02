package com.shengeliia.everydayenglish.screens.intro

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.transition.Scene
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.shengeliia.data.local.PreferencesManager
import com.shengeliia.everydayenglish.R
import com.shengeliia.everydayenglish.screens.launch.LaunchActivity

class IntroActivity : AppCompatActivity(), IntroContract.ViewMVP {

    private val presenter: IntroContract.PresenterMVP = IntroPresenter()
    private lateinit var introLayoutPlaceholder: FrameLayout
    private lateinit var transition: Transition
    private lateinit var nameScene: Scene
    private lateinit var levelScene: Scene

    private lateinit var nameButton: Button
    private lateinit var nameEdit: EditText
    private lateinit var nameMessage: TextView
    private lateinit var levelButton: Button
    private lateinit var levelRadioBar: RadioGroup
    private lateinit var levelImage: ImageView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        introLayoutPlaceholder = findViewById(R.id.introLayoutPlaceholder)
        transition = TransitionInflater.from(this).inflateTransition(R.transition.fade_transition)
        nameScene = Scene.getSceneForLayout(introLayoutPlaceholder, R.layout.intro_name_layout, this)
        levelScene = Scene.getSceneForLayout(introLayoutPlaceholder, R.layout.intro_level_layout, this)
    }

    override fun onStart() {
        super.onStart()
        presenter.register(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.unregister()
    }

    override fun showNameLayout() {
        applyTransition(nameScene)
        nameButton = levelScene.sceneRoot.findViewById(R.id.introNameButton)
        nameButton.isEnabled = false
        nameEdit = levelScene.sceneRoot.findViewById(R.id.introNameEdit)
        nameEdit.addTextChangedListener {
            presenter.onUserEnter(it.toString().trim())
        }
        nameMessage = levelScene.sceneRoot.findViewById(R.id.introNameMessage)
        nameMessage.visibility = View.GONE
        nameButton.setOnClickListener {
            presenter.onUsernameEntered(this, nameEdit.text.toString().trim())
        }
    }

    override fun showLevelLayout() {
        applyTransition(levelScene)
        levelButton = levelScene.sceneRoot.findViewById(R.id.introLevelButton)
        levelRadioBar = levelScene.sceneRoot.findViewById(R.id.introLevelRadioBar)
        levelImage = levelScene.sceneRoot.findViewById(R.id.introLevelImage)
        levelRadioBar.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId) {
                R.id.introBeginnerButton -> animateImage(R.drawable.ic_beginner, levelImage)
                R.id.introAdvancedButton -> animateImage(R.drawable.ic_advanced, levelImage)
            }
        }

        levelButton.setOnClickListener {
            val checkedButton = levelRadioBar.checkedRadioButtonId
            val level = if (checkedButton == R.id.introBeginnerButton) PreferencesManager.LEVEL_BEGINNER else PreferencesManager.LEVEL_ADVANCED
            presenter.onLevelChosen(this, level)
        }
    }

    override fun showLaunchActivity() {
        val intent = Intent(this, LaunchActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun showNameValidationOk() {
        nameMessage.visibility = View.GONE
        nameButton.isEnabled = true
        TransitionManager.beginDelayedTransition(introLayoutPlaceholder)
    }

    override fun showNameValidationError() {
        nameMessage.visibility = View.VISIBLE
        nameButton.isEnabled = false
        TransitionManager.beginDelayedTransition(introLayoutPlaceholder)
    }


    private fun animateImage(resId: Int, image: ImageView) {
        val set = AnimatorSet()

        val translateCenterToLeft = ObjectAnimator.ofFloat(image, "translationX",0f,  -1300f).apply {
            duration = 300
            interpolator = AccelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    image.setImageResource(resId)
                }
            })
        }

        val translateRightToCenter = ObjectAnimator.ofFloat(image, "translationX",1300f, 0f).apply {
            duration = 300
            interpolator = DecelerateInterpolator()
        }

        set.playSequentially(translateCenterToLeft, translateRightToCenter)
        set.start()
    }

    private fun applyTransition(scene: Scene) {
        TransitionManager.go(scene, transition)
    }

}
