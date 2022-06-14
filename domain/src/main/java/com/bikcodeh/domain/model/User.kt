package com.bikcodeh.domain.model

data class User(
    val id: String,
    val name: String,
    val age: Int,
    val profession: String,
    val posts: List<Post>? = null,
    val hobbies: List<Hobby>? = null
)