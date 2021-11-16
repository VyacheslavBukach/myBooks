package com.example.mybooks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val db: FirebaseFirestore
) : ViewModel() {

    fun addToFirestore() {
        val user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
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