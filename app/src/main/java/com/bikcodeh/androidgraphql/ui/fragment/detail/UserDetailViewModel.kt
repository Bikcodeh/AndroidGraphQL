package com.bikcodeh.androidgraphql.ui.fragment.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bikcodeh.common.di.IoDispatcher
import com.bikcodeh.domain.common.Resource
import com.bikcodeh.domain.model.User
import com.bikcodeh.domain.usecase.GetUserDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserDetail: GetUserDetail,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _userDetailState: MutableStateFlow<UserDetailState> =
        MutableStateFlow(UserDetailState.Loading)
    val userDetailState get() = _userDetailState.asStateFlow()

    fun fetchUserDetail(userId: String) {
        viewModelScope.launch(dispatcher) {
            when (val response = getUserDetail(userId)) {
                is Resource.Error -> {
                    _userDetailState.value = UserDetailState.Error(response.message)
                }
                is Resource.Success -> {
                    _userDetailState.value = UserDetailState.UserDetail(response.data)
                }
            }
        }
    }

    sealed class UserDetailState {
        data class UserDetail(val user: User?) : UserDetailState()
        data class Error(val message: String?) : UserDetailState()
        object Loading : UserDetailState()
    }
}