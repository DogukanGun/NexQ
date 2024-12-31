package com.dag.nexq_app.base

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

class UITextHelper @Inject constructor(
    private val context: Context
) {
    fun getString(
        @StringRes resource: Int
    ):String {
        return context.getString(resource)
    }
}