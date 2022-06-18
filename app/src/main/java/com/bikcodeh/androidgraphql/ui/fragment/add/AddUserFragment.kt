package com.bikcodeh.androidgraphql.ui.fragment.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bikcodeh.androidgraphql.R
import com.bikcodeh.androidgraphql.databinding.FragmentAddUserBinding
import com.bikcodeh.androidgraphql.extension.getErrorMessageOrDefault
import com.bikcodeh.androidgraphql.extension.gone
import com.bikcodeh.androidgraphql.extension.isEmpty
import com.bikcodeh.androidgraphql.extension.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddUserFragment : Fragment() {

    private var _binding: FragmentAddUserBinding? = null
    private val binding get() = _binding!!
    private val addUserViewModel: AddUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                addUserViewModel.addUserState.collect { userUiState ->
                    userUiState.error?.let { errorMessage ->
                        binding.addUserLoading.root.gone()
                        requireContext().showToast(
                            requireContext().getErrorMessageOrDefault(errorMessage)
                        )
                    }
                    binding.addUserLoading.root.isVisible = userUiState.isLoading
                    if (userUiState.isSuccess) {
                        requireContext().showToast(getString(R.string.added))
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validateInputs(): Boolean {
        return (!binding.tieAge.isEmpty() || !binding.tieProfession.isEmpty() || !binding.tieUserName.isEmpty())
    }

    private fun setupListeners() {
        binding.btnAddUser.setOnClickListener {
            if (validateInputs())
                addUserViewModel.addUser(
                    binding.tieUserName.text.toString(),
                    binding.tieAge.text.toString().toInt(),
                    binding.tieProfession.text.toString()
                )
            else requireContext().showToast(getString(R.string.required_values))
        }
    }

}