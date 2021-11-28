package com.example.mybooks.presentation.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mybooks.R
import com.example.mybooks.databinding.FragmentRegisterBinding
import com.example.mybooks.presentation.viewmodel.AuthViewModel
import com.example.mybooks.util.AuthState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding: FragmentRegisterBinding by viewBinding()
    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStates()
        initButtons()
    }

    private fun initStates() {
        with(binding) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.authState.collect { authState ->
                    when (authState) {
                        is AuthState.NotLoggedIn -> {
                            progressBar.visibility = View.GONE
                            tvError.visibility = View.GONE
                        }
                        is AuthState.Loading -> {
                            progressBar.visibility = View.VISIBLE
                            tvError.visibility = View.GONE
                        }
                        is AuthState.LoggedIn -> {
                            progressBar.visibility = View.GONE
                            tvError.visibility = View.GONE
                            findNavController().navigate(R.id.action_registerFragment_to_overviewFragment)
                        }
                        is AuthState.Failed -> {
                            progressBar.visibility = View.GONE
                            tvError.visibility = View.VISIBLE
                            tvError.text = authState.message
                            Timber.tag("FIRESTORE_TEST").d("auth failed: ${authState.message}")
                        }
                    }
                }
            }
        }
    }

    private fun initButtons() {
        with(binding) {
            btnRegister.setOnClickListener {
                val email = tvEmail.text.trim()
                val password = tvPassword.text.trim()
                when {
                    email.isEmpty() -> tvEmail.error = "Field is empty"
                    password.isEmpty() -> tvPassword.error = "Field is empty"
                    else -> viewModel.createAccount(email.toString(), password.toString())
                }
            }
            tvLogin.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}