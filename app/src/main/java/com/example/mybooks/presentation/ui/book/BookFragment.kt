package com.example.mybooks.presentation.ui.book

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mybooks.R
import com.example.mybooks.data.Book
import com.example.mybooks.databinding.FragmentBookBinding
import com.example.mybooks.presentation.viewmodel.BookViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookFragment : Fragment(R.layout.fragment_book) {

    private val binding: FragmentBookBinding by viewBinding()
    private val viewModel by viewModels<BookViewModel>()
    private val args: BookFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnBookSave.setOnClickListener {
                viewModel.addToFirestore(Book(title = "fd", author = "d"))
            }
            tvBookTitle.setText(args.bookTitle)
            tvBookAuthor.setText(args.bookAuthor)
        }
    }
}