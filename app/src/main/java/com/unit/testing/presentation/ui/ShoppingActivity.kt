package com.unit.testing.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.unit.testing.databinding.ActivityShoppingBinding
import com.unit.testing.presentation.viewmodel.ShoppingViewModel
import com.unit.testing.presentation.viewmodel.UserRegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingActivity : AppCompatActivity() {

    private val shoppingViewModel by viewModels<ShoppingViewModel>()
    private val binding by lazy { ActivityShoppingBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}