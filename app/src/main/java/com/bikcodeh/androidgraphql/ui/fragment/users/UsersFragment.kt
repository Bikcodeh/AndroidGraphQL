package com.bikcodeh.androidgraphql.ui.fragment.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bikcodeh.androidgraphql.databinding.FragmentUsersBinding
import com.bikcodeh.androidgraphql.extension.getErrorMessageOrDefault
import com.bikcodeh.androidgraphql.extension.gone
import com.bikcodeh.androidgraphql.extension.show
import com.bikcodeh.androidgraphql.ui.adapter.MainAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    private val usersViewModel: UsersViewModel by viewModels()
    private val mainAdapter: MainAdapter by lazy { MainAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()

        lifecycleScope.launch {
            usersViewModel.usersIntent.send(UsersViewModel.MainIntent.FetchUsers)
        }
        observer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI() {
        binding.rvPosts.adapter = mainAdapter
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                usersViewModel.usersState.collect { usersState ->
                    when (usersState) {
                        UsersViewModel.MainState.IdLe -> {}
                        is UsersViewModel.MainState.Loading -> binding.pbLoading.show()
                        is UsersViewModel.MainState.Users -> {
                            mainAdapter.submitList(usersState.users)
                            binding.rvPosts.show()
                            binding.pbLoading.gone()
                        }
                        is UsersViewModel.MainState.Error -> {
                            Toast.makeText(
                                requireContext(),
                                requireContext().getErrorMessageOrDefault(usersState.message),
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.pbLoading.gone()
                        }
                    }
                }
            }
        }
    }
}