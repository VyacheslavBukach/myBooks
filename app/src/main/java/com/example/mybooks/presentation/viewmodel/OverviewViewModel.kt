package com.example.mybooks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybooks.data.Book
import com.example.mybooks.data.books
import com.example.mybooks.util.UiState
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val db: FirebaseFirestore
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Book>>> =
        MutableStateFlow(UiState.Success(listOf()))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = UiState.Loading()
            db.collection("books")
                .get()
                .addOnSuccessListener { result ->
                    val list = mutableListOf<Book>()
                    for (document in result) {
                        val item = Book(
                            title = document.data["title"].toString(),
                            author = document.data["author"].toString()
                        )
                        list.add(item)
                        Timber.tag("FIRESTORE_TEST").d("${document.id} => ${document.data}")
                    }
                    _uiState.value = UiState.Success(list)
                }
                .addOnFailureListener { exception ->
                    _uiState.value = UiState.Failed("$exception")
                    Timber.tag("FIRESTORE_TEST").d("Error getting documents $exception")
                }
        }
    }

    fun addToFirestoreTestDatabase() {
        books.forEach { book ->
            val user = hashMapOf(
                "title" to book.title,
                "author" to book.author
            )
            db.collection("books")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Timber.tag("FIRESTORE_TEST")
                        .d("DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Timber.tag("FIRESTORE_TEST").d("Error adding document $e")
                }
        }
    }
}