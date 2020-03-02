package com.shengeliia.everydayenglish.screens.launch.tests

import com.shengeliia.data.DataRepository
import com.shengeliia.domain.cases.TestsCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestsPresenter : TestsContract.PresenterMVP {
    private var view : TestsContract.ViewMVP? = null
    private var testCase = TestsCase(DataRepository())

    override fun onRefreshed() {
        view?.showLoading()
        GlobalScope.launch (Dispatchers.IO) {
            try {
                val tests = testCase.getRemoteTests()
                withContext(Dispatchers.Main) {
                    view?.showTestList(tests)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view?.showErrorConnectingAlert()
                }
            }
            withContext(Dispatchers.Main) {
                view?.dismissLoading()
            }
        }
    }

    override fun register(view: TestsContract.ViewMVP) {
        this.view = view
        loadTests()
    }

    private fun loadTests() {
        view?.showLoading()
        GlobalScope.launch(Dispatchers.IO) {
            val tests = testCase.getLocalTests()
            if (tests.isNotEmpty()) {
               withContext(Dispatchers.Main) {
                   view?.showTestList(tests)
               }
            } else {
                withContext(Dispatchers.Main) {
                    view?.showNoDataAlert()
                }
            }
            withContext(Dispatchers.Main) {
                view?.dismissLoading()
            }
        }
    }

    override fun unregister() {
        if (view != null) {
            view = null
        }
    }
}