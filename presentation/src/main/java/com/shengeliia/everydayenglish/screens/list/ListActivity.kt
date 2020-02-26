package com.shengeliia.everydayenglish.screens.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.shengeliia.everydayenglish.R
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        setSupportActionBar(listToolbar)
    }
}
