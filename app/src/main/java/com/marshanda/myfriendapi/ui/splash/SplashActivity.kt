package com.marshanda.myfriendapi.ui.splash

import android.os.Bundle
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.tos
import com.marshanda.myfriendapi.R
import com.marshanda.myfriendapi.base.BaseActivity
import com.marshanda.myfriendapi.const.Const
import com.marshanda.myfriendapi.databinding.ActivitySplashBinding
import com.marshanda.myfriendapi.ui.home.HomeActivity
import com.marshanda.myfriendapi.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity :
    BaseActivity<ActivitySplashBinding, SplashViewModel>(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userLogin = session.getString(Const.USER.PROFILE)

        viewModel.splash {
            if (userLogin == "Login") {
                openActivity<HomeActivity>()
            } else {
                openActivity<LoginActivity>()
            }
            finish()
        }
    }
}