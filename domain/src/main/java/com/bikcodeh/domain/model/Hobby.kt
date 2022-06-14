package com.bikcodeh.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hobby(
    val id: String,
    val title: String,
    val description: String
): Parcelable