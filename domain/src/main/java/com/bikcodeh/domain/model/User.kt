package com.bikcodeh.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val name: String,
    val age: Int,
    val profession: String,
    val posts: List<Post>? = null,
    val hobbies: List<Hobby>? = null
): Parcelable