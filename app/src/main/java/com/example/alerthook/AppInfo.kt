
package com.example.alerthook

import android.graphics.drawable.Drawable

data class AppInfo(
    val packageName: String,
    val appName: String,
    val icon: Drawable,
    var isSelected: Boolean = false
)
