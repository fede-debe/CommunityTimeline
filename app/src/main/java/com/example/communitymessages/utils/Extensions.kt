package com.example.communitymessages.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(
    message: Int,
    length: Int = Snackbar.LENGTH_SHORT,
    action: (Snackbar.() -> Unit)? = null
) {
    val snackBar = Snackbar.make(this, message, length)
    action?.let { snackBar.it() }
    snackBar.show()
}