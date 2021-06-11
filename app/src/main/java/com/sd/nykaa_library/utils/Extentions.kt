package com.sd.nykaa_library.utils


import android.util.Log
import com.sd.nykaa_library.BuildConfig
import java.text.SimpleDateFormat
import java.util.*


inline fun Any.log(message: () -> String) {
    if (BuildConfig.DEBUG) {
        Log.d(this::class.java.simpleName, message())
    }
}

fun Long?.format(): String {
    return if (this == null) "" else SimpleDateFormat(
        "hh:mm a",
        Locale.getDefault()
    ).format(Date(this))
}

fun Long.formatTime(): String {
    val minutes: Long = this / 1000 / 60
    return "$minutes mins ${(this / 1000 % 60)} secs"
}

