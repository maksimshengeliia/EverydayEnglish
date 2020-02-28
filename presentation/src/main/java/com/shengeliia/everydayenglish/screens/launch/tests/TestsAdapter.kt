package com.shengeliia.everydayenglish.screens.launch.tests

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.shengeliia.domain.cases.State
import com.shengeliia.domain.models.Test
import com.shengeliia.domain.cases.validateData
import com.shengeliia.everydayenglish.R

class TestsAdapter : RecyclerView.Adapter<TestsAdapter.TestsViewHolder>(), RadioGroup.OnCheckedChangeListener {

    var onItemClickListener: OnItemClickListener? = null
    var onItemLongClickListener: OnItemLongClickListener? = null
    private var list: List<Test> = emptyList()
        set(value) {
            field = value
            currentData = value
        }

    private var state = State.ALL
        set(value) {
            field = value
            currentData = list
        }

    private var currentData: List<Test> = emptyList()
        set(value) {
            field = validateData(value, state)
            notifyDataSetChanged()
        }

    override fun getItemCount() = currentData.size

    override fun onBindViewHolder(holder: TestsViewHolder, position: Int) {
        val test = currentData[position]
        holder.testId = test.id
        holder.title.text = test.name
        holder.progress.text = "${test.solved}/${test.count}"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestsViewHolder {
        return TestsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_test, parent, false))
    }

    fun updateData(list: List<Test>) {
        this.list = list
    }

    interface OnItemClickListener {
        fun onItemClick(testId: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(testId: Int)
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.radio_all -> {
                Log.d("super", "radio_all")
                state = State.ALL
            }
            R.id.radio_new -> {
                Log.d("super", "radio_new")
                state = State.NEW
            }
            R.id.radio_started -> {
                Log.d("super", "radio_started")
                state = State.STARTED
            }
        }
    }

    inner class TestsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.testTitle)
        val progress: TextView = view.findViewById(R.id.testProgressText)
        val icon: ImageView = view.findViewById(R.id.testProgressIcon)
        val root: ConstraintLayout = view.findViewById(R.id.testRoot)

        var testId: Int = 0
        init {
            view.setOnClickListener {
                onItemClickListener?.onItemClick(testId)
            }
            view.setOnLongClickListener {
                onItemLongClickListener?.onItemLongClick(testId)
                true
            }
        }
    }
}