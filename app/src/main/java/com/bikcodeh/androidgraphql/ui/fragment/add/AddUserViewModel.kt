package com.bikcodeh.androidgraphql.ui.fragment.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bikcodeh.common.di.IoDispatcher
import com.bikcodeh.domain.common.Resource
import com.bikcodeh.domain.usecase.AddUserUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel @Inject constructor(
    private val addUserUC: AddUserUC,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _addUserState: MutableStateFlow<AddUserState> = MutableStateFlow(AddUserState.Idle)
    val addUserState: StateFlow<AddUserState> = _addUserState.asStateFlow()

    fun addUser(name: String, age: Int, profession: String) {
        viewModelScope.launch(dispatcher) {
            when (val data = addUserUC(name, age, profession)) {
                is Resource.Error -> {
                    _addUserState.value = AddUserState.Error(data.message)
                }
                is Resource.Success -> _addUserState.value = AddUserState.Response(data.data)
            }
        }
    }

    sealed class AddUserState {
        object Idle : AddUserState()
        object Loading : AddUserState()
        data class Response(val isSuccess: Boolean?) : AddUserState()
        data class Error(val message: String?) : AddUserState()
    }
}