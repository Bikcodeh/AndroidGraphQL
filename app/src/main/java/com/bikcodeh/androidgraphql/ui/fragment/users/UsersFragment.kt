package com.bikcodeh.androidgraphql.ui.fragment.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.apollographql.apollo3.ApolloClient
import com.bikcodeh.UsersSubscription
import com.bikcodeh.androidgraphql.R
import com.bikcodeh.androidgraphql.databinding.FragmentUsersBinding
import com.bikcodeh.androidgraphql.extension.getErrorMessageOrDefault
import com.bikcodeh.androidgraphql.extension.gone
import com.bikcodeh.androidgraphql.extension.show
import com.bikcodeh.androidgraphql.extension.showToast
import com.bikcodeh.androidgraphql.ui.adapter.MainAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UsersFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var apolloClient: ApolloClient

    private val usersViewModel: UsersViewModel by viewModels()
    private val mainAdapter: MainAdapter by lazy {
        MainAdapter { user ->
            val direction = UsersFragmentDirections.actionUsersFragmentToUserDetailFragment(user)
            findNavController().navigate(direction)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            usersViewModel.usersIntent.send(UsersViewModel.MainIntent.FetchUsers)
        }
    }

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
        observer()
        setupListeners()
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
                    binding.usersLoading.root.isVisible = usersState.isLoading
                    if (usersState.error != null) {
                        Toast.makeText(
                            requireContext(),
                            requireContext().getErrorMessageOrDefault(usersState.error),
                            Toast.LENGTH_SHORT
                        ).show()
                        //binding.notConnection.root.show()
                    }
                    if (usersState.users.isNotEmpty()) {
                        mainAdapter.submitList(usersState.users)
                        binding.rvPosts.show()
                    }
                }
            }
        }

        /*viewLifecycleOwner.lifecycleScope.launch {
            apolloClient.subscription(UsersSubscription()).toFlow()
                .collect {
                    requireContext().showToast(it.data?.readUsers?.name ?: "NULL")
                }
        }*/
    }

    private fun setupListeners() {
        binding.notConnection.btnRetry.setOnClickListener {
            lifecycleScope.launch {
                usersViewModel.usersIntent.send(UsersViewModel.MainIntent.FetchUsers)
            }
        }
        binding.fabAddUser.setOnClickListener {
            findNavController().navigate(R.id.action_usersFragment_to_addUserFragment)
        }
    }
}