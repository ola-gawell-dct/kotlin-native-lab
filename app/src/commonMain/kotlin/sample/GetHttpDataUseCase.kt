package sample

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.Url

class GetHttpDataUseCase : UseCase<String>() {

    private val client = HttpClient()

    var address = Url("https://tools.ietf.org/rfc/rfc1866.txt")

    override suspend fun executeOnBackground(): String {
        return client.get(address)
    }

}