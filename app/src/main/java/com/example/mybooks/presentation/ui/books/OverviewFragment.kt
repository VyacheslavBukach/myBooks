package com.example.mybooks.presentation.ui.books

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mybooks.R
import com.example.mybooks.databinding.FragmentOverviewBinding
import com.example.mybooks.presentation.adapter.BookAdapter
import com.example.mybooks.presentation.viewmodel.OverviewViewModel
import com.example.mybooks.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OverviewFragment : Fragment(R.layout.fragment_overview) {

    private val binding: FragmentOverviewBinding by viewBinding()
    private val viewModel by viewModels<OverviewViewModel>()
    private val bookAdapter = BookAdapter { openBookDetail(it.id) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fbAddBook.setOnClickListener {
            findNavController().navigate(R.id.action_overviewFragment_to_bookFragment)
        }
        binding.rvBooks.adapter = bookAdapter

//        viewModel.addToFirestoreTestDatabase()
        viewLifecycleOwner.lifecycleScope.launch {
            with(binding) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            pbProgress.visibility = View.VISIBLE
                        }
                        is UiState.Success -> {
                            bookAdapter.submitList(uiState.data)
                            pbProgress.visibility = View.INVISIBLE
                        }
                        is UiState.Failed -> {
                            pbProgress.visibility = View.INVISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun openBookDetail(id: Long) {

    }
}