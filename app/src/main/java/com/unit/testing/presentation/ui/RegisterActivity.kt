package com.unit.testing.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.unit.testing.databinding.ActivityRegisterBinding
import com.unit.testing.presentation.util.AuthError
import com.unit.testing.presentation.viewmodel.UserRegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private val registerViewModel by viewModels<UserRegisterViewModel>()
    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        with(binding) {
            btnRegister.setOnClickListener {
                registerViewModel.register(
                    username = edtUserName.text.toString(),
                    email = edtEmail.text.toString(),
                    password = edtPassword.text.toString()
                )
            }
        }
        lifecycleScope.launch {

            registerViewModel.emailData.collectLatest {
                binding.edtEmail.error = setAuthError(it)
            }
        }
        lifecycleScope.launch {
            registerViewModel.usernameData.collectLatest {
                binding.edtUserName.error = setAuthError(it)
            }
        }
        lifecycleScope.launch {
            registerViewModel.passwordData.collectLatest {
                binding.edtPassword.error = setAuthError(it)
            }
        }
        lifecycleScope.launch {
            registerViewModel.registerUserData.collectLatest {
                if (!it.isLoading) {
                    binding.btnRegister.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    Log.e("data*", "No Loading")
                    if (it.error.isNotEmpty()) {
                        Log.e("data*", "Error: ${it.error}")
                    } else {
                        if (!it.userEntity.isNullOrEmpty()) {
                            Log.e("data*", "userEntity Not NullOrEmpty")
                            Log.e("data*", it.userEntity.joinToString(','.toString()))
                            binding.edtEmail.setText("")
                            binding.edtUserName.setText("")
                            binding.edtPassword.setText("")

                            Toast.makeText(this@RegisterActivity, "User Created!", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Log.e("data*", "userEntity is NullOrEmpty")
                        }
                    }
                } else {
                    Log.e("data*", "Loading")
                    binding.btnRegister.visibility = View.GONE
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