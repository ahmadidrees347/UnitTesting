package com.unit.testing.domain.usecase

import com.unit.testing.data.local.entity.UserEntity
import com.unit.testing.domain.model.RegisterResult
import com.unit.testing.domain.repository.UserRepository
import com.unit.testing.utils.ValidationUtil
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(
        email: String,
        username: String,
        password: String
    ): RegisterResult {
        val emailError = ValidationUtil.validateEmail(email)
        val usernameError = ValidationUtil.validateUsername(username)
        val passwordError = ValidationUtil.validatePassword(password)

        if (emailError != null || usernameError != null || passwordError != null) {
            return RegisterResult(
                emailError = emailError,
                usernameError = usernameError,
                passwordError = passwordError,
            )
        }

        val result = repository.register(
            UserEntity(
                userName = username.trim(),
                email = email.trim(),
                password = password.trim()
            )
        )
        return RegisterResult(result = result)

    }
}