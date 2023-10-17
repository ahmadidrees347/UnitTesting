package com.unit.testing.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unit.testing.domain.usecase.UserLoginUseCase
import com.unit.testing.presentation.model.LoginUserState
import com.unit.testing.presentation.util.AuthError
import com.unit.testing.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserLoginViewModel @Inject constructor(private val userLoginUseCase: UserLoginUseCase) : ViewModel() {

    private val _loginUserData = MutableStateFlow(LoginUserState())
    var loginUserData: StateFlow<LoginUserState> = _loginUserData

    private val _emailData = MutableStateFlow<AuthError>(AuthError.NONE)
    val emailData: StateFlow<AuthError> = _emailData

    private val _passwordData = MutableStateFlow<AuthError>(AuthError.NONE)
    val passwordData: StateFlow<AuthError> = _passwordData

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val loginResult = userLoginUseCase(
                email = email,
                password = password
            )
            if (loginResult.emailError != null) {
                _emailData.value = loginResult.emailError
            }
            if (loginResult.passwordError != null) {
                _passwordData.value = loginResult.passwordError
            }

            loginResult.result?.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _loginUserData.value = LoginUserState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _loginUserData.value =
                            LoginUserState(userRecord = it.data)
                    }
                    is Resource.Error -> {
                        _loginUserData.value =
                            LoginUserState(error = it.message.toString())
                    }
                }
            }
        }
    }
}