package com.example.mybooks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mybooks.data.Book
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val dbRef: DatabaseReference,
    private val auth: FirebaseAuth
) : ViewModel() {

    fun addToFirestore(title: String, author: String) {
        val uuid = auth.currentUser?.uid
        val userRef = dbRef.child("Users").child(uuid!!)
        val key = userRef.push().key
        val book = Book(uuid = key!!, title = title, author = author)
        userRef.child(key!!).setValue(book)
            .addOnSuccessListener {
                Timber.tag("FIRESTORE_TEST").d("Book added with ID: $uuid")
            }
            .addOnFailureListener { e ->
                Timber.tag("FIRESTORE_TEST").d("Error adding document $e")
            }
    }

    fun updateInFirestore(uuid: String, title: String, author: String) {
        val userUuid = auth.currentUser?.uid
        val book = Book(uuid = uuid, title = title, author = author)
        val post = book.toMap()
        dbRef.child("Users").child(userUuid!!).child(uuid).setValue(post)
            .addOnCompleteListener {
                Timber.tag("FIRESTORE_TEST").d("Book updated with ID: $uuid")
            }
            .addOnFailureListener { e ->
                Timber.tag("FIRESTORE_TEST").d(e)
            }
    }

    fun deleteFromFirestore(uuid: String) {
        val userUuid = auth.currentUser?.uid
        dbRef.child("Users").child(userUuid!!).child(uuid).removeValue()
            .addOnCompleteListener {
                Timber.tag("FIRESTORE_TEST").d("Book deleted with ID: $uuid")
            }
            .addOnFailureListener { e ->
                Timber.tag("FIRESTORE_TEST").d(e)
            }
    }
}