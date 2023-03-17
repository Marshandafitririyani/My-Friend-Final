package com.marshanda.myfriendapi.ui.detail

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.data.CoreSession
import com.google.gson.Gson
import com.marshanda.myfriendapi.api.ApiService
import com.marshanda.myfriendapi.base.BaseViewModel
import com.marshanda.myfriendapi.data.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,

) : BaseViewModel() {

    val user = userDao.getUser()

    val getDetail = userDao.getUser()

    fun getLike(myId: Int?, friendId: Int?) = viewModelScope.launch {
        _apiResponse.send(ApiResponse().responseLoading())
        ApiObserver(
            { apiService.like(id_like = friendId, id = myId) },
            true,
            object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    val liked = response.getBoolean("liked")
                    _apiResponse.send(ApiResponse().responseSuccess(data = liked))
                    Timber.d("cek api like $response")
                }
            }
        )


    }
}