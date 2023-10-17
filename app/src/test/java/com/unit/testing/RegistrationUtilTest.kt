package com.unit.testing

import com.google.common.truth.Truth.assertThat
import com.unit.testing.utils.RegistrationUtil
import org.junit.Test

class RegistrationUtilTest {

    @Test
    fun `empty username returns false`() {
        val result = RegistrationUtil.validateInput(
            "",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `valid username and correctly repeated password returns true`() {
        val result = RegistrationUtil.validateInput(
            "Ahmad",
            "A123456@@",
            "A123456@@"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun `username already exists returns false`() {
        val result = RegistrationUtil.validateInput(
            "Ahmad",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `incorrectly confirmed password returns false`() {
        val result = RegistrationUtil.validateInput(
            "Ahmad",
            "123456",
            "654321"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `empty password returns false`() {
        val result = RegistrationUtil.validateInput(
            "Philipp",
            "",
            ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `less than 2 digit password returns false`() {
        val result = RegistrationUtil.validateInput(
            "Ahmad",
            "Qwerty1",
            "Qwerty1"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid email returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Ahmad",
            "Ahmad",
            "Qwerty1",
            "Qwerty1"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `username with 3 chars returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "ABC",
            "ahmad@gmail.com",
            "Qwerty1@",
            "Qwerty1@"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `username more than 16 chars returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "01234567891234567",
            "ahmad@gmail.com",
            "Qwerty1@",
            "Qwerty1@"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `username more than 5 and less than 16 chars returns true`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Ahmad123",
            "a@q.cc",
            "Qwerty1@",
            "Qwerty1@"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun `password without special char returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Ahmad123",
            "ahmad@gmail.com",
            "qwerty12@",
            "qwerty12@"
        )
        assertThat(result).isFalse()
    }
}