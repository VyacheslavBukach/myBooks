package com.example.mybooks.presentation.ui.book

import androidx.fragment.app.Fragment
import com.example.mybooks.R
import com.example.mybooks.presentation.viewmodel.BookViewModel

class BookFragment : Fragment(R.layout.fragment_book) {

    companion object {
        fun newInstance() = BookFragment()
    }

    private lateinit var viewModel: BookViewModel


}