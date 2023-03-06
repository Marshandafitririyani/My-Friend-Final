package com.marshanda.myfriendapi.ui.detail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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

        // initialButtonLike()

        viewModel.user.observe(this) {
            myUser = it
        }

        // TODO: cukup menggunakan 1 tombol saja
        binding.btnLike.setOnClickListener {
            val myId = myUser?.id
            val friendId = friend?.id
            viewModel.getLike(myId, friendId)
            setResult(Const.LIST.RELOAD) // TODO: apabila tombol di klik berarti ada perubaham like, dan mengirimkan kode result agar list data di reload kembali
        }
        /*binding.btnUnlike.setOnClickListener {
            val myId = myUser?.id
            val friendId = friend?.id
            viewModel.getLike(myId, friendId)
        }*/

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.apiResponse.collect {
                        if (it.status == ApiStatus.LOADING) {
                            loadingDialog.show(getString(R.string.waiting))
                        } else if (it.status == ApiStatus.SUCCESS) {
                            val liked = it.dataAs<Boolean>()
                            binding.detail = friend?.copy(likeByYou = liked)
                            loadingDialog.dismiss()
                        }
                    }
                }
            }
        }
    }

    /*private fun initialButtonLike() {
        if (friend?.like_by_you.equals("true")) {
            binding.btnLike.visibility = View.GONE
            binding.btnUnlike.visibility = View.VISIBLE
        } else {
            binding.btnLike.visibility = View.VISIBLE
            binding.btnUnlike.visibility = View.GONE
        }
    }*/
}

