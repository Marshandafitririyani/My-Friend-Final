package com.marshanda.myfriendapi.ui.save

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.extension.toList
import com.google.gson.Gson
import com.marshanda.myfriendapi.api.ApiService
import com.marshanda.myfriendapi.base.BaseViewModel
import com.marshanda.myfriendapi.data.User
import com.marshanda.myfriendapi.data.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SaveViewModel @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val userDao: UserDao

) : BaseViewModel() {

    val dataList = MutableLiveData<List<User>>()
    val myUser = userDao.getUser()

    fun getList(userId: Int?) = viewModelScope.launch {
        ApiObserver(
            { apiService.listFriend234(userId) },
            false,
            object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    val data = response.getJSONArray(ApiCode.DATA).toList<User>(gson)
                    dataList.postValue(data)
                    Timber.d("cek api ${data.size}")
                }

            }
        )


    }

}