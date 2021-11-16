package com.example.mybooks.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.mybooks.data.Book
import com.example.mybooks.databinding.ItemBookBinding

class BookAdapter(
    private val clickListener: (Book) -> Unit
) : ListAdapter<Book, BookViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding) { position ->
            getItem(position)?.let { book -> clickListener(book) }
        }
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class DiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean =
            oldItem.title == newItem.title && oldItem.author == newItem.author
    }
}