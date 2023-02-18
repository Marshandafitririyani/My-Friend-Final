package com.marshanda.myfriendapi.ui.edit

import androidx.appcompat.app.AppCompatActivity
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
import com.marshanda.myfriendapi.databinding.ActivityEditProfileBinding
import com.marshanda.myfriendapi.databinding.ActivityHomeBinding
import com.marshanda.myfriendapi.ui.home.HomeActivity
import com.marshanda.myfriendapi.ui.home.HomeViewModel
import com.marshanda.myfriendapi.ui.profil.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileActivity : BaseActivity<ActivityEditProfileBinding, EditProfileViewModel>(R.layout.activity_edit_profile) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnSaveEditProfil.setOnClickListener {

            val name = binding.etNameEditProfil.textOf()
            val school = binding.etSchoolEditProfil.textOf()
//            val phone = binding.etPhoneEditProfil.textOf()
            val description = binding.etDsctionEditProfil.textOf()

            viewModel.updateProfil(name,school,description)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect {
                        when (it.status) {
                            ApiStatus.LOADING -> loadingDialog.show(" Please Wait Update")
                            ApiStatus.SUCCESS -> {
                                loadingDialog.dismiss()
                                openActivity<EditProfileActivity>()
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