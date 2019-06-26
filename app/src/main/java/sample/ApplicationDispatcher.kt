package sample

import kotlinx.coroutines.*

internal actual val DispatcherIO: CoroutineDispatcher = Dispatchers.IO
internal actual val DispatcherMain: CoroutineDispatcher = Dispatchers.Main