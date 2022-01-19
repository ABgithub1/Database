package com.example.database.extension

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.getTextOrSetError(): String? {
    return editText?.text?.toString()
        ?.takeIf { it.isNotBlank() }
        .also { text ->
            error = if (text.isNullOrBlank()) {
                "Field is empty"
            } else {
                null
            }
        }
}