package com.unit.testing.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.unit.testing.databinding.ActivityLoginBinding
import com.unit.testing.presentation.util.AuthError
import com.unit.testing.presentation.viewmodel.UserLoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    companion object {
        var userId = 0
    }

    private val loginViewModel by viewModels<UserLoginViewModel>()
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        with(binding) {
            txtShopping.setOnClickListener {
                startActivity(Intent(this@LoginActivity, ShoppingActivity::class.java))
            }
            txtRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
            btnLogin.setOnClickListener {
                loginViewModel.login(
                    email = edtEmail.text.toString(),
                    password = edtPassword.text.toString()
                )
            }
        }
        lifecycleScope.launch {

            loginViewModel.emailData.collectLatest {
                binding.edtEmail.error = setAuthError(it)
            }
        }
        lifecycleScope.launch {
            loginViewModel.passwordData.collectLatest {
                binding.edtPassword.error = setAuthError(it)
            }
        }
        lifecycleScope.launch {
            loginViewModel.loginUserData.collectLatest {
                if (!it.isLoading) {
                    binding.btnLogin.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    Log.e("data*", "No Loading")
                    if (it.error.isNotEmpty()) {
                        Log.e("data*", "Error: ${it.error}")
                    } else {
                        if (it.userRecord != null) {
                            Log.e("data*", "userEntity Not NullOrEmpty")
                            Log.e("data*", "" + it.userRecord)
                            binding.edtEmail.setText("")
                            binding.edtPassword.setText("")

                            userId = it.userRecord.id

                            startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
                        } else {
                            Log.e("data*", "userEntity is NullOrEmpty")
                        }
                    }
                } else {
                    Log.e("data*", "Loading")
                    binding.btnLogin.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
            }

        }
    }

    private fun setAuthError(it: AuthError): String? {
        return when (it) {
            is AuthError.EmptyField -> {
                Log.e("data*", "EmptyField")
                "EmptyField"
            }
            is AuthError.InvalidEmail -> {
                Log.e("data*", "InvalidEmail")
                "InvalidEmail"
            }
            is AuthError.InputTooShort -> {
                Log.e("data*", "InputTooShort")
                "InputTooShort"
            }
            is AuthError.InputTooLarge -> {
                Log.e("data*", "InputTooLarge")
                "InputTooLarge"
            }
            is AuthError.InvalidPassword -> {
                Log.e("data*", "InvalidPassword")
                "InvalidPassword"
            }
            is AuthError.PasswordNotMatch -> {
                Log.e("data*", "PasswordNotMatch")
                "PasswordNotMatch"
            }
            else -> {
                null
            }
        }
    }
}