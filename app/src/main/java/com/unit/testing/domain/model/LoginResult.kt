package com.unit.testing.domain.model

import com.unit.testing.data.local.entity.UserEntity
import com.unit.testing.presentation.util.AuthError
import com.unit.testing.utils.Resource
import kotlinx.coroutines.flow.Flow

data class LoginResult(
    val emailError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: Flow<Resource<UserEntity>>? = null
)
