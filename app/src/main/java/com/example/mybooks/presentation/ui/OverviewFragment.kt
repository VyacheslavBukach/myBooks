package com.example.mybooks.presentation.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mybooks.presentation.viewmodel.OverviewViewModel
import com.example.mybooks.R
import com.example.mybooks.databinding.FragmentOverviewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverviewFragment : Fragment(R.layout.fragment_overview) {

    private val viewBinding: FragmentOverviewBinding by viewBinding()
    private val viewModel by viewModels<OverviewViewModel>()

    companion object {
        fun newInstance() = OverviewFragment()
    }
}