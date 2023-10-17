package com.unit.testing.presentation.viewmodel


import androidx.lifecycle.ViewModel
import com.unit.testing.domain.usecase.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userInfo: UserInfo) : ViewModel() {

    suspend fun getAllUsers() = userInfo.getAllUsers()

    suspend fun getUser(userId: String) = userInfo.getUserById(userId)
}