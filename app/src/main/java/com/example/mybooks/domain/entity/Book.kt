package com.example.mybooks.domain.entity

import com.google.firebase.database.Exclude

data class Book(
    val uuid: String,
    val title: String,
    val author: String,
//    val isReading: Boolean = false
) {

    @Exclude
    fun toMap(): Map<String, Any> {
        return mapOf(
            "uuid" to uuid,
            "title" to title,
            "author" to author,
//            "isReading" to false
        )
    }
}
