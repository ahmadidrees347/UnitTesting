package com.unit.testing.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.unit.testing.databinding.ActivityShoppingBinding
import com.unit.testing.presentation.ui.fragment.ShoppingFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShoppingActivity : AppCompatActivity() {
    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    private val binding by lazy { ActivityShoppingBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(binding.root)
    }
}