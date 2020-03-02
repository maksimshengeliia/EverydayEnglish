package com.shengeliia.everydayenglish.screens.launch.tests

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.shengeliia.domain.TestsState
import com.shengeliia.domain.models.Test
import com.shengeliia.everydayenglish.R

class TestsAdapter : RecyclerView.Adapter<TestsAdapter.TestsViewHolder>(), RadioGroup.OnCheckedChangeListener {

    var onItemClickListener: OnItemClickListener? = null
    var onItemLongClickListener: OnItemLongClickListener? = null
    private var list: List<Test> = emptyList()
    private var filterList: List<Test> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = filterList.size

    override fun onBindViewHolder(holder: TestsViewHolder, position: Int) {
        val test = filterList[position]
        holder.testId = test.id
        holder.title.text = test.name

        val progress = test.quizzesSolved.toFloat() / test.count * 100
        holder.progress.apply {
            text = context.getString(R.string.test_progress, progress.toInt().toString())
        }

        val icon = when(test.state) {
            TestsState.STARTED -> R.drawable.ic_test_started
            TestsState.FINISHED -> R.drawable.ic_test_finished
            TestsState.NEW -> R.drawable.ic_test_new
        }

        holder.icon.setImageResource(icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestsViewHolder {
        return TestsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_test, parent, false))
    }

    fun updateData(list: List<Test>) {
        this.list = list
        this.filterList = filterList(list)
    }

    private fun filterList(list: List<Test>, testsState: TestsState? = null) = when(testsState) {
        TestsState.NEW -> list.filter {
            it.state == TestsState.NEW
        }
        TestsState.STARTED -> list.filter {
            it.state == TestsState.STARTED
        }
        TestsState.FINISHED -> list.filter {
            it.state == TestsState.FINISHED
        }
        else -> list
    }

    interface OnItemClickListener {
        fun onItemClick(testId: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(testId: Int)
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when(checkedId) {
            R.id.radio_all -> {
                filterList = filterList(list)
            }
            R.id.radio_new -> {
                filterList = filterList(list, TestsState.NEW)
            }
            R.id.radio_started -> {
                filterList = filterList(list, TestsState.STARTED)
            }
            R.id.radio_finished -> {
                filterList = filterList(list, TestsState.FINISHED)
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