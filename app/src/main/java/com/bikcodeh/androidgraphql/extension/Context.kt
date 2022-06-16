package com.bikcodeh.androidgraphql.extension

import android.content.Context
import android.widget.Toast
import com.bikcodeh.androidgraphql.R

fun Context.getErrorMessageOrDefault(errorMessage: String?): String {
    return errorMessage ?: getString(R.string.default_error)
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
