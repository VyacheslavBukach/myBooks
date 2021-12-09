package com.example.mybooks

import com.example.mybooks.domain.entity.Book
import com.example.mybooks.domain.repository.Repository
import com.example.mybooks.presentation.viewmodel.BookViewModel
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class BookViewModelTest {

    private val repository: Repository = mockk(relaxed = true)
    private val viewModel = BookViewModel(repository)

    @Test
    fun add() {
        viewModel.addInDatabase(book.title, book.author)
        verify { repository.add(book.title, book.author) }
    }

    @Test
    fun update() {
        viewModel.updateInDatabase(uuid, title, author)
        verify { repository.update(book.uuid, book.title, book.author) }
    }

    @Test
    fun delete() {
        viewModel.deleteFromDatabase(book.uuid)
        verify { repository.delete(book.uuid) }
    }

    companion object {
        private const val title = "Title"
        private const val author = "Author"
        private const val uuid = "uuid"
        private val book = Book(uuid, title, author)
    }
}