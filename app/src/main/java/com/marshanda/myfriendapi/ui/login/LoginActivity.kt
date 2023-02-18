package com.marshanda.myfriendapi.ui.login

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.isEmptyRequired
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.textOf
import com.marshanda.myfriendapi.R
import com.marshanda.myfriendapi.base.BaseActivity
import com.marshanda.myfriendapi.databinding.ActivityLoginBinding
import com.marshanda.myfriendapi.ui.home.HomeActivity
import com.marshanda.myfriendapi.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvRegisterLogin.setOnClickListener {
            openActivity<RegisterActivity>() {
            }
        }

        binding.btnLogin.setOnClickListener {
            if (binding.etPhoneLogin.isEmptyRequired(R.string.label_must_fill) ||
                binding.etPasswordLogin.isEmptyRequired(R.string.label_must_fill)){
                return@setOnClickListener
            }
            val phone = binding.etPhoneLogin.textOf()
            val password = binding.etPasswordLogin.textOf()

            viewModel.login(phone, password)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect {
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show(" Please Wait login")
                            ApiStatus.SUCCESS -> {
                                loadingDialog.dismiss()
                                openActivity<HomeActivity>()
                                finish()
                            }
                            else -> loadingDialog.setResponse(it.message ?: return@collect)
                        }
                    }
                }
            }
        }
    }
}