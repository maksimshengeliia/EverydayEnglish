package com.shengeliia.everydayenglish.screens.intro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shengeliia.everydayenglish.R

class IntroActivity : AppCompatActivity(), IntroContract.ViewMVP {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
    }
}
