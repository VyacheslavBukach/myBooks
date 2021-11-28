package com.example.mybooks.data.repository

import com.example.mybooks.domain.entity.Book
import com.example.mybooks.domain.repository.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealtimeDatabaseRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val dbRef: DatabaseReference
) : Repository {

    override fun add(title: String, author: String) {
        val userUuid = auth.currentUser?.uid
        val userRef = dbRef.child("Users").child(userUuid!!)
        val key = userRef.push().key
        val book = Book(uuid = key!!, title = title, author = author)
        userRef.child(key!!).setValue(book)
            .addOnSuccessListener {
                Timber.tag("FIRESTORE_TEST").d("Book added with ID: $userUuid")
            }
            .addOnFailureListener { e ->
                Timber.tag("FIRESTORE_TEST").d("Error adding document $e")
            }
    }

    override fun update(uuid: String, title: String, author: String) {
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

    override fun delete(uuid: String) {
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