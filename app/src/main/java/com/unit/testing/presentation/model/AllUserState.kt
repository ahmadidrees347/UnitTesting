package com.unit.testing.presentation.model

import com.unit.testing.data.local.entity.UserEntity

data class AllUserState(
    val isLoading: Boolean = false,
    val userRecord: MutableList<UserEntity>? = null,
    val error: String = ""
)