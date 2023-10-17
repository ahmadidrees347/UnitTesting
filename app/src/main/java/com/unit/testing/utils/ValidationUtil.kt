package com.unit.testing.utils

import com.unit.testing.presentation.util.AuthError


object ValidationUtil {
    // Define the special characters you want to check for
    private const val specialCharacters = "`~!@#$%^&*()_=-/*+."
    private const val emailPattern =
        "^[a-zA-Z0-9.!#\$%&'*+=?^_`{|}~-]+@[a-zA-Z]+(?:\\.[a-zA-Z]{2,}+)+\$"


    /**
     * @param email
     * should not be empty
     * EMAIL Regex should be matched
     * */
    fun validateEmail(email: String): AuthError? {
        val emailRegex = emailPattern.toRegex()
        val trimmedEmail = email.trim()

        if (trimmedEmail.isBlank()) {
            return AuthError.EmptyField
        }
        if (!emailRegex.matches(email)) {
            return AuthError.InvalidEmail
        }
        return null
    }

    /**
     * @param username
     * should not be empty
     * should not be less than 5
     * should not be greater than 16
     * */
    fun validateUsername(username: String): AuthError? {
        val trimmedUsername = username.trim()
        if (trimmedUsername.isBlank()) {
            return AuthError.EmptyField
        }
        if (trimmedUsername.length < Constants.MIN_USERNAME_LENGTH) {
            return AuthError.InputTooShort
        }
        if (trimmedUsername.length > Constants.MAX_USERNAME_LENGTH) {
            return AuthError.InputTooLarge
        }
        return null
    }

    /**
     * @param password
     * should not be empty
     * should not be less than 8
     * should not be greater than 16
     * should have UpperCase letter
     * should have Digit letter
     * should have Special character
     * */
    fun validatePassword(password: String): AuthError? {
        if (password.isBlank()) {
            return AuthError.EmptyField
        }
        if (password.length < Constants.MIN_PASSWORD_LENGTH) {
            return AuthError.InputTooShort
        }
        if (password.length > Constants.MAX_PASSWORD_LENGTH) {
            return AuthError.InputTooLarge
        }
        val capitalLettersInPassword = password.any { it.isUpperCase() }
        val specialCharacterInPassword = password.any { specialCharacters.contains(it) }
        val numberInPassword = password.any { it.isDigit() } //(password.count { it.isDigit() } < 2)
        if (!capitalLettersInPassword || !specialCharacterInPassword || !numberInPassword) {
            return AuthError.InvalidPassword
        }
        return null
    }

    /**
     * @param password and
     * @param confirmPassword
     * should not be empty
     * should not be less than 8
     * should not be greater than 16
     * should have UpperCase letter
     * should have Digit letter
     * should have Special character
     * password and confirmPassword should match
     * */
    fun validateConfirmPassword(password: String, confirmPassword: String): AuthError? {
        if (validatePassword(password) != null) {
            return validatePassword(password)
        }
        if (validatePassword(confirmPassword) != null) {
            return validatePassword(password)
        }
        if (password != confirmPassword) {
            return AuthError.PasswordNotMatch
        }
        return null
    }
}