package sample

import kotlinx.coroutines.CoroutineDispatcher

internal expect val DispatcherIO: CoroutineDispatcher
internal expect val DispatcherMain: CoroutineDispatcher

expect class Sample() {
    fun checkMe(): Int
}

expect object Platform {
    val name: String
}

fun hello(): String = "Hello2 from ${Platform.name}"

expect fun log(msg: String)

class Proxy {
    fun proxyHello() = hello()
}

fun main() {
    println(hello())
}