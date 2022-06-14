package com.bikcodeh.androidgraphql.ui.fragment.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bikcodeh.common.di.IoDispatcher
import com.bikcodeh.domain.common.Resource
import com.bikcodeh.domain.usecase.GetUserDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserDetail: GetUserDetail,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    fun fetchUserDetail(userId: String) {
        viewModelScope.launch(dispatcher) {
            val response = getUserDetail(userId)
            when (response) {
                is Resource.Error -> {
                    Timber.e("UserDetailViewModel", response.message ?: "")
                }
                is Resource.ErrorResource -> {
                    Timber.e("UserDetailViewModel", response.message ?: "")
                }
                is Resource.Loading -> {}
                is Resource.Success -> {
                    Timber.e("UserDetailViewModel ${response.data}")
                }
            }
        }
    }
}