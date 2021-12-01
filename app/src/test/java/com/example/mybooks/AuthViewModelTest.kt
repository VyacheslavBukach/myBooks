package com.example.mybooks

import com.example.mybooks.presentation.viewmodel.AuthViewModel
import com.example.mybooks.util.AuthState
import com.google.firebase.auth.FirebaseAuth
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AuthViewModelTest {

    private var auth: FirebaseAuth = mockk()
    private val viewmodel = AuthViewModel(auth)

    @Before
    fun userIsNull() {
        every { auth.currentUser } returns null
    }

    @Test
    fun returnNotLoggedInWhenUserNotLogin() {
        assertEquals(viewmodel.authState.value, AuthState.NotLoggedIn)
    }

    @Test
    fun createAccountSuccessful() {
        coEvery { viewmodel.createAccount("f", "555555") }
        assertEquals(viewmodel.authState.value, AuthState.Loading)
    }
}