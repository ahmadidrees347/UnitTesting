package com.unit.testing.presentation.util

import kotlin.random.Random

sealed class AuthError {
    object NONE : AuthError()
    object EmptyField : AuthError()
    object InvalidEmail : AuthError()
    object InputTooShort : AuthError()
    object InputTooLarge : AuthError()
    object InvalidPassword : AuthError()
    object PasswordNotMatch : AuthError()

    override fun hashCode(): Int {
        return Random.nextInt()
    }

    override fun equals(other: Any?): Boolean {
        return false
    }
}