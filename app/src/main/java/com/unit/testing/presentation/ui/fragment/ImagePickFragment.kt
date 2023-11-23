package com.unit.testing.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.unit.testing.databinding.FragmentImagePickBinding
import com.unit.testing.presentation.adapters.ImageAdapter
import com.unit.testing.presentation.viewmodel.ShoppingViewModel
import com.unit.testing.utils.Constants
import javax.inject.Inject

class ImagePickFragment @Inject constructor(
    val imageAdapter: ImageAdapter
)  : Fragment() {

    lateinit var viewModel: ShoppingViewModel
    private lateinit var binding: FragmentImagePickBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImagePickBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]

        setupRecyclerView()
        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setCurImageUrl(it)
        }
    }

    private fun setupRecyclerView() {
        binding.rvImages.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(requireContext(), Constants.GRID_SPAN_COUNT)
        }
    }
}