package com.unit.testing.presentation.model

import com.unit.testing.data.local.entity.UserEntity

data class UserProfileState(
    val isLoading: Boolean = false,
    val userRecord: UserEntity? = null,
    val error: String = ""
)