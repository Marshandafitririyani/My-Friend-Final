package com.marshanda.myfriendapi.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.data.CoreSession
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
class DetailViewModel  @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val userDao: UserDao,
    private val session: CoreSession

): BaseViewModel() {

    val dataLike = MutableLiveData<List<User>>()
    val user = userDao.getUser()

    val getDetail = userDao.getUser()

    fun getLike(myId: Int?, friendId: Int?) = viewModelScope.launch {
        ApiObserver({ apiService.like(id_like = friendId, id = myId) }, false, object : ApiObserver.ResponseListener {
            override suspend fun onSuccess(response: JSONObject) {
                /*val data =
                    response.getJSONArray(ApiCode.DATA).toList<User>(gson)
                dataLike.postValue(data)*/
                _apiResponse.send(ApiResponse().responseSuccess("Liked"))
                Timber.d("cek api like $response")
            }
        })


    }
}