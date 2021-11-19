package com.example.mybooks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mybooks.data.Book
import com.example.mybooks.util.UiState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val db: FirebaseFirestore
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Book>>> =
        MutableStateFlow(UiState.Success(listOf()))
    val uiState = _uiState.asStateFlow()

    // Firestore listener
    private var reg: ListenerRegistration

    init {
        _uiState.value = UiState.Loading()
        reg = db.collection("books").addSnapshotListener { value, e ->
            if (e != null) {
                Timber.tag("FIRESTORE_TEST").d("Listen failed $e")
                _uiState.value = UiState.Failed("$e")
                return@addSnapshotListener
            }
            val books = mutableListOf<Book>()
            for (document in value!!) {
                val item = Book(
                    uuid = document.id,
                    title = document.data["title"].toString(),
                    author = document.data["author"].toString()
                )
                books.add(item)
            }
            _uiState.value = UiState.Success(books)
            Timber.tag("FIRESTORE_TEST").d("Current books: $books")
        }
    }

    override fun onCleared() {
        super.onCleared()
        reg.remove()
    }

//    fun addToFirestoreTestDatabase() {
//        books.forEach { book ->
//            val user = hashMapOf(
//                "title" to book.title,
//                "author" to book.author
//            )
//            db.collection("books")
//                .add(user)
//                .addOnSuccessListener { documentReference ->
//                    Timber.tag("FIRESTORE_TEST")
//                        .d("DocumentSnapshot added with ID: ${documentReference.id}")
//                }
//                .addOnFailureListener { e ->
//                    Timber.tag("FIRESTORE_TEST").d("Error adding document $e")
//                }
//        }
//    }
}