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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.authState.collect { authState ->
                when (authState) {
                    is AuthState.NotLoggedIn -> {}
                    is AuthState.LoggedIn -> {
                        findNavController().navigate(R.id.action_registerFragment_to_overviewFragment)
                    }
                    is AuthState.Failed -> {
                        Timber.tag("FIRESTORE_TEST").d("auth failed: ${authState.message}")
                    }
                }
            }
        }
        with(binding) {
            btnRegister.setOnClickListener {
                val email = tvEmail.text.toString()
                val password = tvPassword.text.toString()
                viewModel.createAccount(email, password)
            }
            btnLogin.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}