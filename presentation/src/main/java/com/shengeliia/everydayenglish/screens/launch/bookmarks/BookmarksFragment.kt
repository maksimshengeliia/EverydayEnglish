package com.shengeliia.everydayenglish.screens.launch.bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shengeliia.everydayenglish.R

class BookmarksFragment : Fragment(), BookmarksContract.ViewMVP {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bookmarks, container, false)
        return view
    }

    companion object {
        const val TAG = "BookmarksFragment"
    }
}