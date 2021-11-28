package com.example.mybooks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mybooks.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    fun addInDatabase(title: String, author: String) {
        repository.add(title, author)
    }

    fun updateInDatabase(uuid: String, title: String, author: String) {
        repository.update(uuid, title, author)
    }

    fun deleteFromDatabase(uuid: String) {
        repository.delete(uuid)
    }
}