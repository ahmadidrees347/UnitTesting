package com.unit.testing.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unit.testing.domain.usecase.UserProfileUseCase
import com.unit.testing.presentation.model.AllUserState
import com.unit.testing.presentation.model.UserProfileState
import com.unit.testing.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(private val userProfileUseCase: UserProfileUseCase) :
    ViewModel() {

    private val _currentUserData = MutableStateFlow(UserProfileState())
    var currentUserData: StateFlow<UserProfileState> = _currentUserData

    private val _allUserData = MutableStateFlow(AllUserState())
    var allUserData: StateFlow<AllUserState> = _allUserData

    suspend fun getAllUsers() {
        viewModelScope.launch {
            val userProfileResult = userProfileUseCase.getAllUsers()
            userProfileResult.result?.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _allUserData.value = AllUserState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _allUserData.value =
                            AllUserState(userRecord = it.data)
                    }
                    is Resource.Error -> {
                        _allUserData.value =
                            AllUserState(error = it.message.toString())
                    }
                }
            }
        }
    }

    fun getCurrentUserRecord(userId: String) {
        viewModelScope.launch {
            val userProfileResult = userProfileUseCase(userId)
            userProfileResult.result?.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _currentUserData.value = UserProfileState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _currentUserData.value =
                            UserProfileState(userRecord = it.data)
                    }
                    is Resource.Error -> {
                        _currentUserData.value =
                            UserProfileState(error = it.message.toString())
                    }
                }
            }
        }
    }
}