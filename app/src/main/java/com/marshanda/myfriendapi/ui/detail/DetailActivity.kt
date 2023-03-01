package com.marshanda.myfriendapi.ui.detail

import android.os.Bundle
import android.view.View
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

        //menerima detail  list frienda
        friend = intent.getParcelableExtra(Const.LIST.LIST)
        binding.detail = friend

        initialButtonLike()

        viewModel.user.observe(this) {
            myUser = it
        }

        //like
        binding.btnLike.setOnClickListener {
            val myId = myUser?.id
            val friendId = friend?.id
            viewModel.getLike(myId, friendId)
        }
        binding.btnUnlike.setOnClickListener {
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

    private fun initialButtonLike(){
        if (friend?.like_by_you.equals("true")) {
            binding.btnLike.visibility = View.GONE
            binding.btnUnlike.visibility = View.VISIBLE
        } else{
            binding.btnLike.visibility = View.VISIBLE
            binding.btnUnlike.visibility = View.GONE
        }
    }
}

