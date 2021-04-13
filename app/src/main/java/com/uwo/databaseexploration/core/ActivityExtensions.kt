package com.uwo.databaseexploration.core

import android.content.Intent
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.finishWithResult(resultCode: Int, resultPreparer: (Intent.() -> Unit)? = null) {
    setResult(resultCode, resultPreparer?.let { Intent().apply(it) })
    finish()
}