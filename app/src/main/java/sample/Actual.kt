package sample

import android.util.Log
import kotlinx.coroutines.*

internal actual val MainDispatcher: CoroutineDispatcher = Dispatchers.Main

actual fun log(msg: String) {
    Log.d("Custom Log", msg)
}