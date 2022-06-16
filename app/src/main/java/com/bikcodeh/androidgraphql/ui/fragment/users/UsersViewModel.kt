package com.bikcodeh.androidgraphql.ui.fragment.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bikcodeh.common.di.IoDispatcher
import com.bikcodeh.domain.common.Resource
import com.bikcodeh.domain.model.User
import com.bikcodeh.domain.usecase.GetUsersUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersUC: GetUsersUC,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    val usersIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _usersState: MutableStateFlow<MainState> = MutableStateFlow(MainState.IdLe)
    val usersState: StateFlow<MainState> = _usersState

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            usersIntent.consumeAsFlow().collect {
                when (it) {
                    MainIntent.FetchUsers -> getUsers()
                }
            }
        }
    }

    private fun getUsers() {
        viewModelScope.launch(dispatcher) {
            getUsersUC()
                .collect {
                    when (it) {
                        is Resource.Error -> {
                            _usersState.value = MainState.Error(it.message)
                        }
                        is Resource.ErrorResource -> {
                            _usersState.value = MainState.Error(it.message)
                        }
                        is Resource.Loading -> {
                            _usersState.value = MainState.Loading(isLoading = true)
                        }
                        is Resource.Success -> {
                            _usersState.value = MainState.Users(it.data ?: emptyList())
                        }
                    }
                }
        }
    }

    sealed class MainState {
        object IdLe : MainState()
        data class Loading(val isLoading: Boolean) : MainState()
        data class Users(val users: List<User>) : MainState()
        data class Error(val message: String?) : MainState()
    }

    sealed class MainIntent {
        object FetchUsers : MainIntent()
    }
}

