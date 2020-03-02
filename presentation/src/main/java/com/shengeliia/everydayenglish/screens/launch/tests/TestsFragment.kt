package com.shengeliia.everydayenglish.screens.launch.tests

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.shengeliia.domain.models.Test
import com.shengeliia.everydayenglish.R
import com.shengeliia.everydayenglish.screens.quiz.QuizActivity

class TestsFragment : Fragment(), TestsContract.ViewMVP, SwipeRefreshLayout.OnRefreshListener {

    private val testsAdapter = TestsAdapter()
    private val presenter = TestsPresenter()
    private lateinit var filterBar: RadioGroup
    private lateinit var listView: RecyclerView
    private lateinit var refreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        testsAdapter.onItemClickListener = object : TestsAdapter.OnItemClickListener {
            override fun onItemClick(testId: Int) {
                val intent = Intent(activity, QuizActivity::class.java)
                intent.putExtra(QuizActivity.TEST_ID, testId)
                startActivity(intent)
            }
        }

        testsAdapter.onItemLongClickListener = object : TestsAdapter.OnItemLongClickListener {
            override fun onItemLongClick(testId: Int) {

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tests, container, false)
        findView(view)

        listView.apply {
            adapter = testsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        filterBar.setOnCheckedChangeListener(testsAdapter)
        refreshLayout.setOnRefreshListener(this)
        return view
    }

    private fun findView(view: View) {
        filterBar = view.findViewById(R.id.testFilterBar)
        listView = view.findViewById(R.id.testList)
        refreshLayout = view.findViewById(R.id.testRefreshLayout)
    }

    override fun onStart() {
        super.onStart()
        presenter.register(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.unregister()
    }


    override fun showTestList(list: List<Test>) {
        testsAdapter.updateData(list)
        filterBar.check(R.id.radio_all)
    }

    override fun showErrorConnectingAlert() {
        Toast.makeText(activity, "Error connecting", Toast.LENGTH_LONG).show()
    }

    override fun showNoDataAlert() {
        Toast.makeText(activity, "No data", Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        refreshLayout.isRefreshing = true
    }

    override fun dismissLoading() {
        refreshLayout.isRefreshing = false
    }

    override fun onRefresh() {
        presenter.onRefreshed()
    }

    companion object {
        const val TAG = "TestsFragment"
    }

}
