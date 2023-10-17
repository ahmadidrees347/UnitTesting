package com.unit.testing.presentation.model

import com.unit.testing.data.local.entity.UserEntity

data class RegisterUserState(
    val isLoading: Boolean = false,
    val userEntity: List<Long>? = null,
    val error: String = ""
)