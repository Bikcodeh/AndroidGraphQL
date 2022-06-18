package com.bikcodeh.androidgraphql.ui.fragment.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bikcodeh.common.di.IoDispatcher
import com.bikcodeh.domain.common.fold
import com.bikcodeh.domain.model.User
import com.bikcodeh.domain.usecase.GetUsersUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersUC: GetUsersUC,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    val usersIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _usersState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState())
    val usersState: StateFlow<MainUiState> = _usersState

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
        getUsersUC.invoke().map { result ->
            result.fold(
                onSuccess = { users ->
                    _usersState.update { it.copy(users = users ?: emptyList()) }
                },
                onFailure = { error ->
                    _usersState.update { it.copy(error = error) }
                }
            )
        }.onStart {
            _usersState.update { it.copy(isLoading = true) }
        }.onCompletion {
           _usersState.update { it.copy(isLoading = false) }
        }.flowOn(dispatcher).launchIn(viewModelScope)
    }

    data class MainUiState(
        val isLoading: Boolean = false,
        val users: List<User> = emptyList(),
        val error: String? = null
    )

    sealed class MainIntent {
        object FetchUsers : MainIntent()
    }
}

