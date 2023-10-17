package com.unit.testing.domain.usecase

import com.unit.testing.domain.model.LoginResult
import com.unit.testing.domain.repository.UserRepository
import com.unit.testing.utils.ValidationUtil
import javax.inject.Inject

class UserLoginUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(
        email: String,
        password: String
    ): LoginResult {
        val emailError = ValidationUtil.validateEmail(email)
        val passwordError = ValidationUtil.validatePassword(password)

        if (emailError != null || passwordError != null) {
            return LoginResult(
                emailError = emailError,
                passwordError = passwordError,
            )
        }

        val result = repository.getUserByEmailAndPassword(
            email = email.trim(),
            password = password.trim()
        )
        return LoginResult(result = result)

    }
}