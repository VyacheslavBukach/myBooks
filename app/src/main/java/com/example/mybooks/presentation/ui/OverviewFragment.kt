package com.example.mybooks.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mybooks.presentation.viewmodel.OverviewViewModel
import com.example.mybooks.R
import com.example.mybooks.data.books
import com.example.mybooks.databinding.FragmentOverviewBinding
import com.example.mybooks.presentation.adapter.BookAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class OverviewFragment : Fragment(R.layout.fragment_overview) {

    private val binding: FragmentOverviewBinding by viewBinding()
    private val viewModel by viewModels<OverviewViewModel>()
    private val bookAdapter = BookAdapter { openBookDetail(it.id) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvBooks.adapter = bookAdapter
        binding.rvReadingBooks.adapter = bookAdapter
        bookAdapter.submitList(books)
    }

    private fun openBookDetail(id: Long) {

    }

    companion object {
        fun newInstance() = OverviewFragment()
    }
}