package sample

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.Url
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ApplicationApi {
    private val client = HttpClient()

    var address = Url("https://tools.ietf.org/rfc/rfc1866.txt")

    fun about(callback: (String) -> Unit) {
        GlobalScope.apply {
            launch(DispatcherIO) {
                val result: String = client.get<String>(address)

                callback(result)
            }
        }
    }
}