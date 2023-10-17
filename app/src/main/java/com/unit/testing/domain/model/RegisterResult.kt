package com.unit.testing.domain.model

import com.unit.testing.presentation.util.AuthError
import com.unit.testing.utils.Resource
import kotlinx.coroutines.flow.Flow

data class RegisterResult(
    val emailError: AuthError? = null,
    val usernameError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: Flow<Resource<List<Long>>>? = null
)
