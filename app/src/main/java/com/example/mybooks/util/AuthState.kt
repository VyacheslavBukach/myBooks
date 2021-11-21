package com.example.mybooks.util

sealed class AuthState {
    object NotLoggedIn : AuthState()
    object LoggedIn : AuthState()
    data class Failed(val message: String) : AuthState()
}