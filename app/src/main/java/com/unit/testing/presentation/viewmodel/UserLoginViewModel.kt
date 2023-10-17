package com.unit.testing.presentation.viewmodel


import androidx.lifecycle.ViewModel
import com.unit.testing.data.local.entity.UserEntity
import com.unit.testing.domain.usecase.UserLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserLoginViewModel @Inject constructor(private val userLogin: UserLogin) : ViewModel() {





}