package com.example.mybooks.presentation.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mybooks.R
import com.example.mybooks.databinding.FragmentRestoreBinding
import com.example.mybooks.presentation.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestoreFragment : Fragment(R.layout.fragment_restore) {

    private val binding: FragmentRestoreBinding by viewBinding()
    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            binding.btnRestore.setOnClickListener {
                val email = tvEmail.text.trim()
                when {
                    email.isEmpty() -> tvEmail.error = "Field is empty"
                    else -> {
                        viewModel.restore(email.toString())
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }
}