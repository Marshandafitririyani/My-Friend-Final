package com.marshanda.myfriendapi.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.crocodic.core.base.adapter.CoreListAdapter
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.tos
import com.marshanda.myfriendapi.R
import com.marshanda.myfriendapi.base.BaseActivity
import com.marshanda.myfriendapi.const.Const
import com.marshanda.myfriendapi.data.User
import com.marshanda.myfriendapi.databinding.ActivityHomeBinding
import com.marshanda.myfriendapi.databinding.CustomListFriendBinding
import com.marshanda.myfriendapi.ui.detail.DetailActivity
import com.marshanda.myfriendapi.ui.profil.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(R.layout.activity_home) {

    private val list = ArrayList<User?>()
    private val listAll = ArrayList<User?>()
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //fungsi untuk filter search
        binding?.srcHome?.doOnTextChanged { text, start, before, count ->
            if (list.isEmpty()) {
                tos("kosong")
            }
            if (text.isNullOrEmpty()) {
                list.clear()
                binding?.rcRecyclerView?.adapter?.notifyDataSetChanged()
                list.addAll(listAll)
                binding?.rcRecyclerView?.adapter?.notifyItemInserted(0)
            } else {
                val filter = listAll?.filter { it?.name?.contains("$text", true) == true }
                list.clear()
                filter?.forEach {
                    list.add(it)
                }
                binding?.rcRecyclerView?.adapter?.notifyDataSetChanged()
                binding?.rcRecyclerView?.adapter?.notifyItemInserted(0)
            }
        }

        binding.ivProfilHome.setOnClickListener {
            openActivity<ProfileActivity>()
        }

        binding.ivLoguot.setOnClickListener {
            viewModel.logout { tos("Logout") }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            user?.id?.let { viewModel.getList(it) }
        }

        binding.rcRecyclerView.adapter =
            CoreListAdapter<CustomListFriendBinding, User>(R.layout.custom_list_friend)
                .initItem(list) { position, data ->
                    openActivity<DetailActivity> {
                        putExtra(Const.LIST.LIST, data)
                    }
                }

        viewModel.myUser.observe(this) {
            user = it
            viewModel.getList(it.id)
        }

        viewModel.dataList.observe(this@HomeActivity) {
            binding.swipeRefreshLayout.isRefreshing = false
            list.clear()
            list.addAll(it)
            binding?.rcRecyclerView?.adapter?.notifyDataSetChanged()

            if (list.isEmpty()) {
                binding?.tvKosong?.visibility = View.VISIBLE
            } else {
                binding?.tvKosong?.visibility = View.GONE
            }
            Timber.d("cek data note $it")
            listAll.clear()
            listAll.addAll(it)
        }
    }
}
