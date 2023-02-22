package com.marshanda.myfriendapi.ui.detail

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.tos
import com.marshanda.myfriendapi.R
import com.marshanda.myfriendapi.base.BaseActivity
import com.marshanda.myfriendapi.const.Const
import com.marshanda.myfriendapi.data.User
import com.marshanda.myfriendapi.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity :
    BaseActivity<ActivityDetailBinding, DetailViewModel>(R.layout.activity_detail) {

    private var friend: User? = null
    private var myUser: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        friend = intent.getParcelableExtra(Const.LIST.LIST)
        binding.detail = friend

        viewModel.user.observe(this) {
            myUser = it
        }
        binding.btnLike.setOnClickListener {
            val myId = myUser?.id
            val friendId = friend?.id
            viewModel.getLike(myId, friendId)
        }

        lifecycleScope.launch {
            viewModel.apiResponse.collect {
                if (it.status == ApiStatus.SUCCESS) {

                    tos("like")

                } else {
                    tos("don't like")
                }
            }
        }
    }
}