package com.example.mybooks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val db: FirebaseFirestore
) : ViewModel() {

    fun addToFirestore(title: String, author: String) {
        val user = hashMapOf(
            "title" to title,
            "author" to author
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

    fun updateInFirestore(uuid: String, title: String, author: String) {
        db.collection("books")
            .document(uuid)
            .update("title", title, "author", author)
            .addOnSuccessListener {
                Timber.tag("FIRESTORE_TEST").d("DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e ->
                Timber.tag("FIRESTORE_TEST").d("Error updating document $e")
            }
    }

    fun deleteFromFirestore(uuid: String) {
        db.collection("books")
            .document(uuid)
            .delete()
            .addOnSuccessListener { }
            .addOnFailureListener { }
    }
}