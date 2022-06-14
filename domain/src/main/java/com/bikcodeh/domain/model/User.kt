package com.bikcodeh.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val name: String,
    val age: Int,
    val profession: String,
    var posts: List<Post>? = null,
    var hobbies: List<Hobby>? = null
): Parcelable