package com.shengeliia.everydayenglish.screens.launch.bookmarks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shengeliia.domain.models.Test
import com.shengeliia.everydayenglish.R

class BookmarksAdapter : RecyclerView.Adapter<BookmarksAdapter.BookmarksViewHolder>() {
    private var bookmarks: List<Test> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarksViewHolder {
        return BookmarksViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_test, parent, false))
    }

    override fun getItemCount(): Int = bookmarks.size
    override fun onBindViewHolder(holder: BookmarksViewHolder, position: Int) {
    }

    inner class BookmarksViewHolder(view: View): RecyclerView.ViewHolder(view)

}