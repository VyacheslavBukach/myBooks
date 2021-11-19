package com.example.mybooks.data

data class Book(
    val uuid: String = "",
    val title: String,
    val author: String,
    val isReading: Boolean = false
)
