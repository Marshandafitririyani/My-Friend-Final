package com.marshanda.myfriendapi.ui.edit

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.toObject
import com.google.gson.Gson
import com.marshanda.myfriendapi.api.ApiService
import com.marshanda.myfriendapi.base.BaseViewModel
import com.marshanda.myfriendapi.data.User
import com.marshanda.myfriendapi.data.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val userDao: UserDao

) : BaseViewModel() {

    fun updateProfil(name: String, school: String, description: String) = viewModelScope.launch {
        _apiResponse.send(ApiResponse().responseLoading())
        val idUser = userDao.getId().id
        ApiObserver(
            { apiService.updateProfil(idUser, name, school, description) }, false,
            object : ApiObserver.ResponseListener {
                override suspend fun onSuccess(response: JSONObject) {
                    Log.d("cek data", response.toString())
                    val data = response.getJSONObject(ApiCode.DATA).toObject<User>(gson)
                    userDao.insert(data.copy(idRoom = 1))
                    _apiResponse.send(ApiResponse().responseSuccess("profile updated"))
                    val message = response.getString(ApiCode.MESSAGE)
                    _apiResponse.send(ApiResponse(status = ApiStatus.SUCCESS, message = message))

                }
            }
        )
    }

    fun updateProfileWithPhoto(name: String, school: String?, description: String, photo: File) =
        viewModelScope.launch {
            val fileBody = photo.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val filePart = MultipartBody.Part.createFormData("photo", photo.name, fileBody)
            _apiResponse.send(ApiResponse().responseLoading())
            val idUser = userDao.getId().id
            ApiObserver(
                { apiService.updateProfileWithPhoto(idUser, name, school, description, filePart) },
                false,
                object : ApiObserver.ResponseListener {
                    override suspend fun onSuccess(response: JSONObject) {

                        val data = response.getJSONObject(ApiCode.DATA).toObject<User>(gson)
                        userDao.insert(data.copy(idRoom = 1))
                        _apiResponse.send(ApiResponse().responseSuccess("profile updated"))
                    }
                    override suspend fun onError(response: ApiResponse) {
                        super.onError(response)
                        Log.d("cek photo", response.toString())
                        _apiResponse.send(ApiResponse().responseError())
                    }


                }
            )
        }
}