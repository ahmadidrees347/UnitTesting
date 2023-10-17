package com.unit.testing.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unit.testing.domain.usecase.RegisterUserUseCase
import com.unit.testing.presentation.model.RegisterUserState
import com.unit.testing.presentation.util.AuthError
import com.unit.testing.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserRegisterViewModel @Inject constructor
    (private val registerUseCase: RegisterUserUseCase) : ViewModel() {


    private val _registerUserData = MutableStateFlow(RegisterUserState())
    var registerUserData: StateFlow<RegisterUserState> = _registerUserData

    private val _emailData = MutableStateFlow<AuthError>(AuthError.NONE)
    val emailData: StateFlow<AuthError> = _emailData

    private val _usernameData = MutableStateFlow<AuthError>(AuthError.NONE)
    val usernameData: StateFlow<AuthError> = _usernameData

    private val _passwordData = MutableStateFlow<AuthError>(AuthError.NONE)
    val passwordData: StateFlow<AuthError> = _passwordData


    fun register(email: String, username: String, password: String) {
        viewModelScope.launch {
            val registerResult = registerUseCase(
                email = email,
                username = username,
                password = password
            )
            if (registerResult.emailError != null) {
                _emailData.value = registerResult.emailError
            }
            if (registerResult.usernameError != null) {
                _usernameData.value = registerResult.usernameError
            }
            if (registerResult.passwordError != null) {
                _passwordData.value = registerResult.passwordError
            }

            registerResult.result/*?.onStart {
                _registerUserData.value = RegisterUserState(isLoading = true)
            }?.catch { e ->
                _registerUserData.value = RegisterUserState(error = e.message.toString())
            }*/?.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _registerUserData.value = RegisterUserState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _registerUserData.value =
                            RegisterUserState(userEntity = it.data)
                    }
                    is Resource.Error -> {
                        _registerUserData.value =
                            RegisterUserState(error = it.message.toString())
                    }
                }
            }
        }
    }

//    fun registerNew(email: String, username: String, password: String) {
//        viewModelScope.launch {
//            val registerResult = registerUseCase(
//                email = email,
//                username = username,
//                password = password
//            )
//            if (registerResult.emailError != null) {
//                registerErrorLive.value = registerResult.emailError
//            }
//            if (registerResult.usernameError != null) {
//                registerErrorLive.value = registerResult.usernameError
//            }
//            if (registerResult.passwordError != null) {
//                registerErrorLive.value = registerResult.passwordError
//            }
//            when (registerResult.result) {
//                is Resource.Loading -> {
//                    _registerUserData.value = RegisterUserState(isLoading = true)
//                }
//                is Resource.Success -> {
//                    _registerUserData.value =
//                        RegisterUserState(userEntity = registerResult.result.data)
//                }
//                is Resource.Error -> {
//                    _registerUserData.value =
//                        RegisterUserState(error = registerResult.result.message.toString())
//                }
//                else -> {
//                    _registerUserData.value = RegisterUserState(isLoading = false)
//                }
//            }
//        }
//    }

}