package com.example.mybooks

import com.example.mybooks.presentation.viewmodel.OverviewViewModel
import com.example.mybooks.util.AuthState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class OverviewViewModelTest {

    private var auth: FirebaseAuth = mockk(relaxed = true)
    private val dbRef: DatabaseReference = mockk(relaxed = true)
    private lateinit var viewModel: OverviewViewModel

    @Before
    fun setup() {
        every { auth.currentUser } returns null
        viewModel = OverviewViewModel(dbRef, auth)
    }

    @Test
    fun logout() {
        viewModel.logOut()
        assertEquals(auth.currentUser, null)
    }

    @Test
    fun whenTheUserIsNotLogIn() {
        assertEquals(viewModel.authState.value, AuthState.NotLoggedIn)
    }
}