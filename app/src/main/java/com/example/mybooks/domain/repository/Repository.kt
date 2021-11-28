package com.example.mybooks.domain.repository

interface Repository {
    fun add(title: String, author: String)
    fun update(uuid: String, title: String, author: String)
    fun delete(uuid: String)
}