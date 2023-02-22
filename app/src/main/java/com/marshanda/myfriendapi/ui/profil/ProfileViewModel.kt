package com.marshanda.myfriendapi.ui.profil

import com.marshanda.myfriendapi.base.BaseViewModel
import com.marshanda.myfriendapi.data.User
import com.marshanda.myfriendapi.data.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userDao: UserDao,

    ) : BaseViewModel() {

    private val _user = kotlinx.coroutines.channels.Channel<List<User>>()
    val user = _user.receiveAsFlow()

    val getUser = userDao.getUser()


}