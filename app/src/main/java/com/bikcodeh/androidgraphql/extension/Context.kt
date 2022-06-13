package com.bikcodeh.androidgraphql.extension

import android.content.Context
import com.bikcodeh.androidgraphql.R

fun Context.getErrorMessageOrDefault(errorMessage: String?): String {
    return errorMessage ?: getString(R.string.default_error)
}
