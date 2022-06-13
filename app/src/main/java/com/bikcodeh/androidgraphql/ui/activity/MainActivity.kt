package com.bikcodeh.androidgraphql.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bikcodeh.androidgraphql.extension.getErrorMessageOrDefault
import com.bikcodeh.androidgraphql.databinding.ActivityMainBinding
import com.bikcodeh.androidgraphql.extension.gone
import com.bikcodeh.androidgraphql.extension.show
import com.bikcodeh.androidgraphql.ui.adapter.MainAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val mainAdapter: MainAdapter by lazy { MainAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()

        lifecycleScope.launch {
            mainViewModel.postsIntent.send(MainIntent.FetchPosts)
        }
        observer()
    }

    private fun setupUI() {
        binding.rvPosts.adapter = mainAdapter
    }

    private fun observer() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.postsState.collect { postsState ->
                    when (postsState) {
                        is MainState.Error -> {
                            Toast.makeText(
                                this@MainActivity,
                                baseContext.getErrorMessageOrDefault(postsState.message),
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.pbLoading.gone()
                        }
                        MainState.IdLe -> {}
                        is MainState.Loading -> {
                            binding.pbLoading.show()
                        }
                        is MainState.Posts -> {
                            mainAdapter.submitList(postsState.posts)
                            binding.rvPosts.show()
                            binding.pbLoading.gone()
                        }
                    }
                }
            }
        }
    }
}