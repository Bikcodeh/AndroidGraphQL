package com.bikcodeh.androidgraphql.extension

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun TextInputEditText.afterChanged(
    inputLayout: TextInputLayout,
    afterTextChanged: (String) -> Unit
) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun TextInputEditText.isEmpty(): Boolean {
    var isEmpty = false
    this.text?.let {
        isEmpty = TextUtils.isEmpty(it.toString())
    }
    return isEmpty
}