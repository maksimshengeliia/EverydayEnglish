package com.shengeliia.everydayenglish.screens.launch.tests

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shengeliia.domain.models.Test
import com.shengeliia.everydayenglish.R
import com.shengeliia.everydayenglish.screens.quiz.QuizActivity

class TestsFragment() : Fragment() {

    private lateinit var testsAdapter: TestsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        testsAdapter = TestsAdapter()
        testsAdapter.onItemClickListener = object : TestsAdapter.OnItemClickListener {
            override fun onItemClick(testId: Int) {
                val intent = Intent(activity, QuizActivity::class.java)
                intent.putExtra(QuizActivity.TEST_ID, testId)
                startActivity(intent)
            }
        }

        testsAdapter.onItemLongClickListener = object : TestsAdapter.OnItemLongClickListener {
            override fun onItemLongClick(testId: Int) {
                Toast.makeText(activity, "LongClick!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tests, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.testFragList)
        recyclerView.apply {
            adapter = testsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        testsAdapter.updateData(listOf(
            Test(1, "masha", 1, 3),
            Test(2, "tasha", 1, 3),
            Test(3, "lena", 1, 3),
            Test(4, "ficha", 1, 3),
            Test(5, "ficha", 1, 3),
            Test(6, "ficha", 1, 3),
            Test(7, "ficha", 1, 3),
            Test(8, "ficha", 1, 3),
            Test(9, "ficha", 1, 3)
        ))
        return view
    }

    companion object {
        const val TAG = "TestsFragment"
    }
}
