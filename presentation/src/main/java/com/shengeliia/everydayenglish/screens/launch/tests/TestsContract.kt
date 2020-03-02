package com.shengeliia.everydayenglish.screens.launch.tests

import com.shengeliia.domain.models.Test
import com.shengeliia.everydayenglish.BasePresenter

interface TestsContract {
    interface PresenterMVP: BasePresenter<ViewMVP> {
        fun onRefreshed()
    }

    interface ViewMVP {
        fun showTestList(list: List<Test>)
        fun showErrorConnectingAlert()
        fun showNoDataAlert()
        fun showLoading()
        fun dismissLoading()
    }
}