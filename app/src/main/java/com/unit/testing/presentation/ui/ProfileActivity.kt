package com.unit.testing.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.unit.testing.databinding.ActivityProfileBinding
import com.unit.testing.presentation.viewmodel.UserProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    private val profileViewModel by viewModels<UserProfileViewModel>()
    private val binding by lazy { ActivityProfileBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launch {
            profileViewModel.getAllUsers()
        }
        lifecycleScope.launch {
            profileViewModel.allUserData.collectLatest {
                if (!it.isLoading) {
                    Log.e("data*", "No Loading")
                    if (it.error.isNotEmpty()) {
                        Log.e("data*", "Error: ${it.error}")
                    } else {
                        if (it.userRecord != null) {
                            Log.e("data*", "userEntity Not NullOrEmpty")
                            Log.e("data*", it.userRecord.toString())
                            binding.txtRecordsInDb.text = it.userRecord.size.toString()
                        } else {
                            Log.e("data*", "userEntity is NullOrEmpty")
                        }
                    }
                } else {
                    Log.e("data*", "Loading")
                }
            }
        }
        lifecycleScope.launch {
            profileViewModel.getCurrentUserRecord("${LoginActivity.userId}")
            profileViewModel.currentUserData.collectLatest {
                if (!it.isLoading) {
                    Log.e("data*", "No Loading")
                    if (it.error.isNotEmpty()) {
                        Log.e("data*", "Error: ${it.error}")
                    } else {
                        if (it.userRecord != null) {
                            Log.e("data*", "userEntity Not NullOrEmpty")
                            Log.e("data*", it.userRecord.toString())
                            binding.txtEmail.text = it.userRecord.email
                            binding.txtUserName.text = it.userRecord.userName
                            binding.txtPassword.text = it.userRecord.password
                        } else {
                            Log.e("data*", "userEntity is NullOrEmpty")
                        }
                    }
                } else {
                    Log.e("data*", "Loading")
                }
            }

        }
    }
}