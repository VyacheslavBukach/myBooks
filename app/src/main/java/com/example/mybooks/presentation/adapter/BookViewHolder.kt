package com.example.mybooks.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mybooks.R
import com.example.mybooks.data.Book
import com.example.mybooks.databinding.ItemBookBinding

class BookViewHolder(
    private val binding: ItemBookBinding,
    clickAtPosition: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener {
            clickAtPosition(absoluteAdapterPosition)
        }
    }

    fun bind(book: Book) {
        binding.apply {
            tvBookTitle.text = book.title
            tvBookAuthor.text = book.author
            ivBook.load(R.drawable.boy_read) {
                crossfade(true)
            }
        }
    }
}