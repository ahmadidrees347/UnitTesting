package com.unit.testing.utils


object RegistrationUtil {

    /**
     * the input is not valid if...
     * --> password is empty
     * --> password should not be username
     * --> the confirmed password is not the same as the real password
     * --> the password contains less than 2 digits
     * --> the password contains no Upper Case letter
     */
    fun validateInput(
        username: String,
        password: String,
        confirmedPassword: String
    ): Boolean {
        if (username.isEmpty() || password.isEmpty()) {
            return false
        }
        if (username == password) {
            return false
        }
        if (password != confirmedPassword) {
            return false
        }
        if (password.count { it.isDigit() } < 2) {
            return false
        }
        if (password.count { it.isUpperCase() } < 1) {
            return false
        }
        return true
    }

    /**
     * Validate
     * --> username
     * --> email
     * --> password
     * --> confirm password
     * --> the confirmed password is not the same as the real password
     */
    fun validateRegistrationInput(
        username: String,
        email: String,
        password: String,
        confirmedPassword: String
    ): Boolean {
        return (ValidationUtil.validateUsername(username) == null &&
                ValidationUtil.validateEmail(email) == null &&
                ValidationUtil.validatePassword(password) == null &&
                ValidationUtil.validatePassword(confirmedPassword) == null &&
                ValidationUtil.validateConfirmPassword(password, confirmedPassword) == null)
    }
}