package com.bikcodeh.androidgraphql.ui.activity

import com.bikcodeh.domain.model.User

sealed class MainState {
    object IdLe: MainState()
    data class Loading(val isLoading: Boolean): MainState()
    data class Users(val users: List<User>): MainState()
    data class Error(val message: String?): MainState()
}

sealed class MainIntent {
    object FetchUsers: MainIntent()
}