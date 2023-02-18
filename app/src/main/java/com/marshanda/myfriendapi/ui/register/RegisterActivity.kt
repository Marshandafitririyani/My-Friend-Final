package com.marshanda.myfriendapi.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.isEmptyRequired
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.textOf
import com.crocodic.core.extension.tos
import com.marshanda.myfriendapi.R
import com.marshanda.myfriendapi.base.BaseActivity
import com.marshanda.myfriendapi.databinding.ActivityRegisterBinding
import com.marshanda.myfriendapi.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>(R.layout.activity_register) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.ivBackRegister.setOnClickListener {
            openActivity<LoginActivity>()
        }

        binding.btnRegister.setOnClickListener {
            if (binding.etNameRegister.isEmptyRequired(R.string.label_must_fill) ||
                binding.etPhoneRegister.isEmptyRequired(R.string.label_must_fill) ||
                binding.etPasswordRegister.isEmptyRequired(R.string.label_must_fill)
            ) {
                return@setOnClickListener
            }
            val name = binding.etNameRegister.textOf()
            val phone = binding.etPhoneRegister.textOf()
            val password = binding.etPasswordRegister.textOf()

            viewModel.register(name,phone, password)

        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect {
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show("Please Wait Register")
                            ApiStatus.SUCCESS -> { loadingDialog.show("Succes")
                                openActivity<LoginActivity>()
                                tos("Please Login")
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