package com.example

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal actual val MainDispatcher: CoroutineDispatcher = Dispatchers.Main

actual fun log(msg: String) {
    Log.d("Custom Log", msg)
}