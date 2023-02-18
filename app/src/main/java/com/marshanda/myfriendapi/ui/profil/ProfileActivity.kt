package com.marshanda.myfriendapi.ui.profil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.crocodic.core.extension.isEmptyRequired
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.textOf
import com.crocodic.core.extension.toObject
import com.marshanda.myfriendapi.R
import com.marshanda.myfriendapi.base.BaseActivity
import com.marshanda.myfriendapi.data.User
import com.marshanda.myfriendapi.databinding.ActivityLoginBinding
import com.marshanda.myfriendapi.databinding.ActivityProfilBinding
import com.marshanda.myfriendapi.ui.edit.EditProfileActivity
import com.marshanda.myfriendapi.ui.home.HomeActivity
import com.marshanda.myfriendapi.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Response.error

@AndroidEntryPoint
class ProfileActivity :
    BaseActivity<ActivityProfilBinding, ProfileViewModel>(R.layout.activity_profil) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnEditProfil.setOnClickListener {
           openActivity<EditProfileActivity> {  }

        }

        lifecycleScope.launch {
            viewModel.getUser.observe(this@ProfileActivity) {
                it?.let { data ->
                    binding.user = data
                    binding.let { viewImage ->
                        //untuk profilenya
                        Glide
                            .with(this@ProfileActivity)
                            .load(it.photo)
                            .placeholder(R.drawable.picture)
                            .error(R.drawable.no_image)
                            .apply(RequestOptions.centerInsideTransform())
                            .into(viewImage.ivImageProfil)
                    }
                }
            }
        }


    }

}