package com.marshanda.myfriendapi.ui.profil

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.crocodic.core.extension.openActivity
import com.marshanda.myfriendapi.R
import com.marshanda.myfriendapi.base.BaseActivity
import com.marshanda.myfriendapi.databinding.ActivityProfilBinding
import com.marshanda.myfriendapi.ui.edit.EditProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileActivity :
    BaseActivity<ActivityProfilBinding, ProfileViewModel>(R.layout.activity_profil) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnEditProfile.setOnClickListener {
           openActivity<EditProfileActivity> {  }

        }

        binding.btnEditProfile.setOnClickListener {
            val kembali = Intent(this, EditProfileActivity::class.java).apply {
                putExtra("photo", binding?.user?.photo, )
                putExtra("username", binding?.user?.name, )
                putExtra("schoolname", binding?.user?.school)
                putExtra("descriptionname", binding?.user?.description)
            }
            startActivity(kembali)
        }

        lifecycleScope.launch {
            viewModel.getUser.observe(this@ProfileActivity) {
                it?.let { data ->
                    binding.user = data
                    binding.let { viewImage ->
                        Glide
                            .with(this@ProfileActivity)
                            .load(it.photo)
                            .placeholder(R.drawable.img_picture)
                            .error(R.drawable.img_no_image)
                            .apply(RequestOptions.centerInsideTransform())
                            .into(viewImage.ivImageProfil)
                    }
                }
            }
        }


    }

}