package com.unit.testing.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.unit.testing.databinding.FragmentAddShoppingItemBinding
import com.unit.testing.presentation.viewmodel.ShoppingViewModel
import com.unit.testing.utils.Status
import com.unit.testing.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddShoppingItemFragment @Inject constructor(
    private val glide: RequestManager
) : Fragment() {

    lateinit var viewModel: ShoppingViewModel
    private lateinit var binding: FragmentAddShoppingItemBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddShoppingItemBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]
        subscribeToObserver()

        with(binding) {
            btnAddShoppingItem.setOnClickListener {
                viewModel.insertShoppingItem(
                    etShoppingItemName.text.toString(),
                    etShoppingItemAmount.text.toString(),
                    etShoppingItemPrice.text.toString()
                )
            }

            ivShoppingImage.setOnClickListener {
                findNavController().navigate(
                    AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
                )
            }
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setCurImageUrl("")
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun subscribeToObserver() {
        viewModel.curImageUrl.observe(viewLifecycleOwner) {
            glide.load(it).into(binding.ivShoppingImage)
        }
        viewModel.insertShoppingItemStatus.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        binding.root.showSnackBar("Item Added")
                        findNavController().popBackStack()
                    }

                    Status.ERROR -> {
                        binding.root.showSnackBar("Error occurred")
                    }

                    else -> {
                        binding.root.showSnackBar("Unknown Msg")
                    }
                }
            }
        }
    }
}