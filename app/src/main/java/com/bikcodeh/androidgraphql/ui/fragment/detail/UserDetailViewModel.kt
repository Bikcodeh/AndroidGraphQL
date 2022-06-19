package com.bikcodeh.androidgraphql.ui.fragment.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bikcodeh.common.di.IoDispatcher
import com.bikcodeh.domain.common.fold
import com.bikcodeh.domain.model.User
import com.bikcodeh.domain.usecase.GetUserDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserDetail: GetUserDetail,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _userDetailState: MutableStateFlow<UserDetailUiState> =
        MutableStateFlow(UserDetailUiState())
    val userDetailState: StateFlow<UserDetailUiState> get() = _userDetailState.asStateFlow()

    fun fetchUserDetail(userId: String) {
        viewModelScope.launch(dispatcher) {
            _userDetailState.update { currentState -> currentState.copy(isLoading = true) }
            getUserDetail(userId)
                .fold(
                    onSuccess = { user ->
                        _userDetailState.update { currentState -> currentState.copy(user = user) }
                        _userDetailState.update { currentState -> currentState.copy(isLoading = false) }
                    },
                    onFailure = { error ->
                        _userDetailState.update { currentState -> currentState.copy(error = error) }
                        _userDetailState.update { currentState -> currentState.copy(isLoading = false) }
                    }
                )
        }
    }

    data class UserDetailUiState(
        val user: User? = null,
        val error: String? = null,
        val isLoading: Boolean = false
    )

    sealed class UserDetailState {
        data class UserDetail(val user: User?) : UserDetailState()
        data class Error(val message: String?) : UserDetailState()
        object Loading : UserDetailState()
    }
}