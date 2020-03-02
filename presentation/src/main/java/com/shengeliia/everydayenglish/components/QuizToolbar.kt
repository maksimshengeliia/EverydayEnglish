package com.shengeliia.everydayenglish.components

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.shengeliia.everydayenglish.R
import com.shengeliia.everydayenglish.utils.dpToPx

class QuizToolbar : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var finishedQuestionNumber = 0
    private var questionNumber = 0
    private val progressBar = QuizProgressBar(context)
    private val backButton = ImageView(context)
    private val progressText = TextView(context)
    var onBackButtonPressedListener: (() -> Unit)? = null

    init {
        progressBar.barHeight = dpToPx(context, 3)
        addView(
            progressBar,
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.BOTTOM
            }
        )

        backButton.setOnClickListener {
            onBackButtonPressedListener?.invoke()
        }
        backButton.setImageResource(R.drawable.ic_back_arrow)
        addView(
            backButton,
            LayoutParams(dpToPx(context, 30), dpToPx(context, 30)).apply {
                gravity = Gravity.START or Gravity.CENTER_VERTICAL
                marginStart = dpToPx(context, 10)
            }
        )

        addView(
            progressText,
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
            }
        )
    }

    fun setProgress(currentProgress: List<Boolean>) {
        finishedQuestionNumber = currentProgress.size
        progressBar.setProgress(currentProgress)
        showProgressText()
    }

    fun setQuestionNumber(num: Int) {
        questionNumber = num
        progressBar.barsCount = num
    }

    private fun showProgressText() {
        progressText.text = context.getString(R.string.toolbar_progress, finishedQuestionNumber.toString(), questionNumber.toString())
    }

}