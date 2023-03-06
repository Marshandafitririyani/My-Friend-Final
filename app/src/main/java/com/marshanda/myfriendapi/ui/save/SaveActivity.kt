package com.marshanda.myfriendapi.ui.save

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crocodic.core.base.adapter.CoreListAdapter
import com.crocodic.core.extension.openActivity
import com.marshanda.myfriendapi.R
import com.marshanda.myfriendapi.base.BaseActivity
import com.marshanda.myfriendapi.const.Const
import com.marshanda.myfriendapi.data.User
import com.marshanda.myfriendapi.databinding.ActivitySaveBinding
import com.marshanda.myfriendapi.databinding.CustomListFriendBinding
import com.marshanda.myfriendapi.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SaveActivity : BaseActivity<ActivitySaveBinding, SaveViewModel>(R.layout.activity_save) {

    private val list = ArrayList<User?>()
    private var user: User? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observe()
        getTourList()

        binding.rvFriendSave.adapter =
            CoreListAdapter<CustomListFriendBinding, User>(R.layout.custom_list_friend)
                .initItem(list) { position, data ->
                    openActivity<DetailActivity> {
                        putExtra(Const.LIST.LIST, data)

                    }

                }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    getTourList()
                }
            }
        }
    }

    private fun getTourList() {
        viewModel.myUser.observe(this) {
            user = it
            user?.id?.let { viewModel.getList(it) }
        }
    }

    private fun observe() {
        viewModel.dataList.observe(this) {
            val filterByLike = it.filter { it.likeByYou ?: false }
            list.clear()
            binding?.rvFriendSave?.adapter?.notifyDataSetChanged()
            list.addAll(filterByLike)
            binding?.rvFriendSave?.adapter?.notifyItemInserted(0)
            if (list.isEmpty()) {
                binding.tvEmptySave.visibility = View.VISIBLE
            } else {
                binding.tvEmptySave.visibility = View.GONE
            }

        }
    }
}