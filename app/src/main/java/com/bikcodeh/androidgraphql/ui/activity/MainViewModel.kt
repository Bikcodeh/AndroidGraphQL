package com.bikcodeh.androidgraphql.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bikcodeh.domain.common.Resource
import com.bikcodeh.domain.repository.MainRepository
import com.bikcodeh.domain.usecase.GetUsersUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUsersUC: GetUsersUC
) : ViewModel() {

    val postsIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _postsState: MutableStateFlow<MainState> = MutableStateFlow(MainState.IdLe)
    val postsState: StateFlow<MainState> = _postsState

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            postsIntent.consumeAsFlow().collect {
                when (it) {
                    MainIntent.FetchPosts -> getData()
                    else -> {}
                }
            }
        }
    }

    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            getUsersUC()
                .collect {
                    when (it) {
                        is Resource.Error -> {
                            _postsState.value = MainState.Error(it.message)
                        }
                        is Resource.ErrorResource -> {
                            _postsState.value = MainState.Error(it.message)
                        }
                        is Resource.Loading -> {
                            _postsState.value = MainState.Loading(isLoading = true)
                        }
                        is Resource.Success -> {
                            _postsState.value = MainState.Posts(it.data ?: emptyList())
                        }
                    }
                }
        }
    }

}