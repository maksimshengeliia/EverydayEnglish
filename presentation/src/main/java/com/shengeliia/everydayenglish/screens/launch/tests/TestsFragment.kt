package com.shengeliia.everydayenglish.screens.launch.tests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shengeliia.everydayenglish.R

class TestsFragment() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tests, container, false)
        return view
    }

    companion object {
        const val TAG = "TestsFragment"
    }
}
