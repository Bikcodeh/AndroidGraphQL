package com.bikcodeh.androidgraphql.ui.activity

import com.bikcodeh.domain.model.Post

sealed class MainState {
    object IdLe: MainState()
    data class Loading(val isLoading: Boolean): MainState()
    data class Posts(val posts: List<Post>): MainState()
    data class Error(val message: String?): MainState()
}

sealed class MainIntent {
    object FetchPosts: MainIntent()
}