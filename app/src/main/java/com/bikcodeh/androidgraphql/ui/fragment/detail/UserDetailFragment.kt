package com.bikcodeh.androidgraphql.ui.fragment.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bikcodeh.androidgraphql.databinding.FragmentUserDetailBinding
import com.bikcodeh.androidgraphql.extension.getErrorMessageOrDefault
import com.bikcodeh.androidgraphql.extension.gone
import com.bikcodeh.androidgraphql.extension.show
import com.bikcodeh.androidgraphql.ui.adapter.HobbiesAdapter
import com.bikcodeh.androidgraphql.ui.adapter.PostsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailFragment : Fragment() {

    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!
    private val args: UserDetailFragmentArgs by navArgs()
    private val detailViewModel: UserDetailViewModel by viewModels()
    private val postsAdapter: PostsAdapter by lazy { PostsAdapter() }
    private val hobbiesAdapter: HobbiesAdapter by lazy { HobbiesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel.fetchUserDetail(args.userSelected.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvHobbyPost.adapter = postsAdapter
        binding.rvHobbies.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvHobbies.adapter = hobbiesAdapter
        setupObservers()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewModel.userDetailState.collect { detailState ->
                    when (detailState) {
                        is UserDetailViewModel.UserDetailState.Error -> {
                            requireContext().getErrorMessageOrDefault(
                                detailState.message
                            )
                            binding.pbLoadingDetail.gone()
                        }
                        UserDetailViewModel.UserDetailState.Loading -> binding.pbLoadingDetail.show()
                        is UserDetailViewModel.UserDetailState.UserDetail -> {
                            with(binding) {
                                tvAge.text = detailState.user?.age.toString()
                                tvProfession.text = detailState.user?.profession
                                tvName.text = detailState.user?.name
                                postsAdapter.submitList(detailState.user?.posts)
                                hobbiesAdapter.submitList(detailState.user?.hobbies)
                                pbLoadingDetail.gone()
                                mcvUserInfo.show()
                            }
                        }
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}