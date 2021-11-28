package com.example.mybooks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mybooks.data.Book
import com.example.mybooks.util.AuthState
import com.example.mybooks.util.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val dbRef: DatabaseReference,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Book>>> =
        MutableStateFlow(UiState.Success(listOf()))
    val uiState = _uiState.asStateFlow()

    private val _authState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.LoggedIn)
    val authState = _authState.asStateFlow()

    // Firebase listener
    private lateinit var booksListener: ValueEventListener

    init {
        if (auth.currentUser != null) {
            loggedIn()
        } else {
            _authState.value = AuthState.NotLoggedIn
        }
    }

    private fun loggedIn() {
        _authState.value = AuthState.LoggedIn
        _uiState.value = UiState.Loading()
        booksListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataSnapshot = snapshot.child("Users").child(auth.currentUser!!.uid)
                val books = dataSnapshot.children.reversed().map { snap ->
                    val value = snap.value as HashMap<*, *>?
                    val uuid = value?.get("uuid").toString()
                    val title = value?.get("title").toString()
                    val author = value?.get("author").toString()
                    Book(uuid = uuid, title = title, author = author)
                }
                _uiState.value = UiState.Success(books)
                Timber.tag("FIRESTORE_TEST").d("Current books: $books")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        dbRef.addValueEventListener(booksListener)
    }

    fun logOut() {
        auth.signOut()
        _authState.value = AuthState.NotLoggedIn
    }

    override fun onCleared() {
        super.onCleared()
        dbRef.removeEventListener(booksListener)
    }
}