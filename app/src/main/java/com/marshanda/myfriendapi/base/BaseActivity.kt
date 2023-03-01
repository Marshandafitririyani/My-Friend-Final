package com.marshanda.myfriendapi.base

import androidx.databinding.ViewDataBinding
import com.crocodic.core.base.activity.CoreActivity
import com.crocodic.core.base.viewmodel.CoreViewModel
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.clearNotification
import com.crocodic.core.extension.openActivity
import com.marshanda.myfriendapi.data.AppDatabase
import com.marshanda.myfriendapi.ui.splash.SplashActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

open class BaseActivity<VB : ViewDataBinding, VM : CoreViewModel>(layoutRes: Int) :
    CoreActivity<VB, VM>(layoutRes) {

    @Inject
    lateinit var session: CoreSession

    @Inject
    lateinit var appDatabase: AppDatabase

    override fun authLogoutSuccess() {
        super.authLogoutSuccess()
        loadingDialog.dismiss()
        expiredDialog.dismiss()
        clearNotification()
        session.clearAll()
        CoroutineScope(Dispatchers.Default).launch {
            appDatabase.clearAllTables()
        }
        openActivity<SplashActivity>()
        finishAffinity()
    }
}
