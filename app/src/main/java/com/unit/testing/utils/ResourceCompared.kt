package com.unit.testing.utils

import android.content.Context

class ResourceCompared {
    fun isEqual(context: Context, resId: Int, string: String): Boolean {
        return context.getString(resId) == string
    }
}