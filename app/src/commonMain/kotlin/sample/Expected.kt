package sample

import kotlinx.coroutines.CoroutineDispatcher

expect fun log(msg: String)

internal expect val MainDispatcher: CoroutineDispatcher