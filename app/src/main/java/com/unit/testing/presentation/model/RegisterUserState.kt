package com.unit.testing.presentation.model

data class RegisterUserState(
    val isLoading: Boolean = false,
    val userEntity: List<Long>? = null,
    val error: String = ""
)