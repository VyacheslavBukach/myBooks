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

    fun addToFirestore() {
        val user = hashMapOf(
            "title" to "Ada",
            "author" to "Lovelace"
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