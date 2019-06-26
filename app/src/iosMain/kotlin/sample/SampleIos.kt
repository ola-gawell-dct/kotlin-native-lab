package sample

actual class Sample {
    actual fun checkMe() = 7
}

actual object Platform {
    actual val name: String = "iOS3"
}

actual fun log(msg: String) {
    print(msg)
}