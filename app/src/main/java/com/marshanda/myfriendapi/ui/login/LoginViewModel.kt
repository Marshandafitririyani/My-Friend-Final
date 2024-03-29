package com.marshanda.myfriendapi.ui.login

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.toObject
import com.google.gson.Gson
import com.marshanda.myfriendapi.api.ApiService
import com.marshanda.myfriendapi.base.BaseViewModel
import com.marshanda.myfriendapi.const.Const
import com.marshanda.myfriendapi.data.User
import com.marshanda.myfriendapi.data.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val userDao: UserDao,
    private val session: CoreSession

): BaseViewModel()  {
    fun login(phone: String, password: String) = viewModelScope.launch {
        _apiResponse.send(ApiResponse().responseLoading())
        ApiObserver({apiService.login(phone, password)}, false, object : ApiObserver.ResponseListener{
            override suspend fun  onSuccess(response: JSONObject) {
                val data = response.getJSONObject(ApiCode.DATA).toObject<User>(gson)
                val status = response.getInt(ApiCode.STATUS)
                userDao.insert(data.copy(idRoom = 1))
                _apiResponse.send(ApiResponse().responseSuccess())
//                session.setValue(Const.USER.EMAIL,phone)
//                session.setValue(Const.USER.PASSWORD, password)
                session.setValue(Const.USER.PROFILE, "Login")
                if (status == ApiCode.SUCCESS){
                    val message = response.getString(ApiCode.MESSAGE)
                    _apiResponse.send(ApiResponse(status = ApiStatus.SUCCESS, message = message ))
                }
            }
        })
    }

}