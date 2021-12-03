package com.example.mybooks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mybooks.util.AuthState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _authState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.NotLoggedIn)
    val authState = _authState.asStateFlow()

    init {
        if (auth.currentUser != null) _authState.value = AuthState.LoggedIn
        else _authState.value = AuthState.NotLoggedIn
    }

    fun createAccount(email: String, password: String) {
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.tag("FIRESTORE_TEST").d("createUserWithEmail:success")
                    _authState.value = AuthState.LoggedIn
                } else {
                    // If sign in fails, display a message to the user.
                    _authState.value = AuthState.Failed("${task.exception?.message}")
                    Timber.tag("FIRESTORE_TEST").d("createUserWithEmail:failure ${task.exception}")
                }
            }
    }

    fun signIn(email: String, password: String) {
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.tag("FIRESTORE_TEST").d("signInWithEmail:success")
                    _authState.value = AuthState.LoggedIn
                } else {
                    // If sign in fails, display a message to the user.
                    _authState.value = AuthState.Failed("${task.exception?.message}")
                    Timber.tag("FIRESTORE_TEST").d("signInWithEmail:failure ${task.exception}")
                }
            }
    }

    fun restore(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.tag("FIRESTORE_TEST").d("Email sent")
                }
            }
    }
}