package com.bikcodeh.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(val id: String, val comment: String): Parcelable
