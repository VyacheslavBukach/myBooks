package com.example.mybooks.data

data class Book(
    val id: Long = 0,
    val title: String,
    val author: String,
    val isReading: Boolean = false
)
