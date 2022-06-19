package com.bikcodeh.androidgraphql.ui.fragment.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bikcodeh.common.di.IoDispatcher
import com.bikcodeh.domain.common.Resource
import com.bikcodeh.domain.common.fold
import com.bikcodeh.domain.usecase.AddUserUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel @Inject constructor(
    private val addUserUC: AddUserUC,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _addUserState: MutableStateFlow<AddUserUiState> = MutableStateFlow(AddUserUiState())
    val addUserState: StateFlow<AddUserUiState> = _addUserState.asStateFlow()

    fun addUser(name: String, age: Int, profession: String) {
        viewModelScope.launch(dispatcher) {
            addUserUC(name, age, profession).fold(
                onSuccess = { isSuccess ->
                    _addUserState.update { currentState -> currentState.copy(isSuccess = isSuccess ?: false) }
                },
                onFailure = { error ->
                    _addUserState.update { currentState -> currentState.copy(error = error) }
                }
            )
        }
    }

    data class AddUserUiState(
        val isSuccess: Boolean = false,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}